package service;

import dto.Users.Controller.*;
import entity.Users;
import mapper.UserMapper;
import mapper.converter.ResultSetToUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.UserExistRepository;
import repository.UsersRepository;
import treats.updates.UpdateStatusUser;
import treats.hashed.PasswordHashed;
import treats.validators.InsertUserValidator;
import treats.validators.UpdateUserValidator;
import treats.validators.load.LoadValidationResult;
import treats.validators.logopass.LoginError;
import treats.validators.logopass.UpdatePasswordError;
import utils.exceptions.DBExceptions;
import utils.exceptions.ServiceException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {
    private static final Logger logger = LogManager.getLogger();
    private final PasswordHashed passwordHashed;
    private final UsersRepository userRepository;
    private final UpdateUserValidator updateUserValidator;

    private final InsertUserValidator insertUserValidator;

    public UserService(PasswordHashed passwordHashed, UsersRepository userRepository,
                       UpdateUserValidator updateUserValidator, InsertUserValidator insertUserValidator) {
        this.passwordHashed = passwordHashed;
        this.userRepository = userRepository;
        this.updateUserValidator = updateUserValidator;
        this.insertUserValidator = insertUserValidator;
    }

    public void insertUser(UserInsertControllerDTO userInsertControllerDTO) throws DBExceptions {
        final LoadValidationResult result = insertUserValidator.isValid(new UserExistRepository(),
                UserMapper.map(userInsertControllerDTO));
        if (result == null || result.isEmpty()) {
            String salt = passwordHashed.generateSalt();
            Users user = UserMapper.map(userInsertControllerDTO, salt, passwordHashed::passwordHash);
            userRepository.insert(user);
        } else {
            throw new RuntimeException();
        }
    }

    public UsersControllerDTO selectUser(UserLoginControllerDTO dto) throws DBExceptions, SQLException, ServiceException {
        ResultSet rs = userRepository.getByLogin(dto.login());
        Users user;
        if (rs.next()) {
            user = ResultSetToUser.convertWithoutPassword(rs);
        } else {
            user = null;
        }
        if (user == null) {
            logger.error("User with " + dto.login() + " not exists!");
            throw new ServiceException("UserNotFound");
        } else {
            return UserMapper.map(user);
        }
    }

    public Optional<LoginError> checkLogin(UserLoginControllerDTO dto) throws DBExceptions, SQLException {
        ResultSet rs = userRepository.getByLogin(dto.login());
        Users user;
        if (rs.next()) {
            user = ResultSetToUser.convert(rs);
        } else {
            user = null;
        };
        if (user != null) {
            UserPasswordAndSaltControllerDTO psdto = UserMapper.mapToPSController(user);
            if (psdto.userPassword().equals(passwordHashed.passwordHash(dto.password(), psdto.salt()))) {
                return Optional.empty();
            } else {
                return Optional.of(LoginError.PASSWORD_FAILED);
            }
        } else {
            return Optional.of(LoginError.USER_NOT_EXISTS);
        }
    }

    public UsersControllerDTO updateDescriptionUser(UserUpdateDescriptionControllerDTO dto, UpdateStatusUser updateStatus,
                                                    UserConstFieldsControllerDTO constFields) throws DBExceptions, ServiceException {
        Users user = UserMapper.map(dto);
        if (userRepository.updateDescriptionCreation(user, updateStatus)) {
            return UserMapper.map(constFields, dto);
        } else {
            logger.error("Update user: " + user.toString() + "failed");
            throw new ServiceException("UpdateFailed");
        }
    }

    public Optional<UpdatePasswordError> updatePasswordError(UserLogoPassControllerDTO logopass) throws DBExceptions, SQLException, ServiceException {
        ResultSet rs = userRepository.getByLogin(logopass.login());
        rs.next();
        Users oldUser = ResultSetToUser.convert(rs);
        if (oldUser != null) {
            UserPasswordAndSaltControllerDTO dto = UserMapper.mapToPSController(oldUser);
            if (dto.userPassword().equals(passwordHashed.passwordHash(logopass.oldPass(), dto.salt()))) {
                if (!updateUserValidator.isValidPassword(logopass.newPass())) {
                    return Optional.of(UpdatePasswordError.BAD_NEW_PASSWORD);
                }
                String salt = passwordHashed.generateSalt();
                Users newUser = UserMapper.map(logopass, salt, passwordHashed::passwordHash);
                if (userRepository.updatePassword(newUser)) {
                    return Optional.empty();
                } else {
                    logger.error("User not update!");
                    throw new ServiceException("UpdateFailed");
                }
            } else {
                return Optional.of(UpdatePasswordError.ERROR_PASSWORD);
            }
        } else {
            logger.error("No such user with login = " + logopass.login());
            throw new ServiceException("UserNotFound");
        }
    }

    public boolean deleteUser(long id) throws DBExceptions {
        return userRepository.delete(id);
    }

    public List<UserForAdminControllerDTO> selectAllUsers() throws DBExceptions, SQLException {
        ResultSet rs = userRepository.getAll();
        List<Users> users = new ArrayList<>();
        while (rs.next()) {
            users.add(ResultSetToUser.convert(rs));
        }
        return users.stream().map(UserMapper::mapToAdminController).collect(Collectors.toList());
    }

    public UserForAdminControllerDTO selectUserByLogin(String login) throws DBExceptions, SQLException {
        logger.error(login);
        ResultSet rs = userRepository.getByLogin(login);
        if (rs.next()) {
            return UserMapper.mapToAdminController(Objects.requireNonNull(ResultSetToUser.convert(rs)));
        } else {
            logger.error("No results!");
            return null;
        }
    }
}
