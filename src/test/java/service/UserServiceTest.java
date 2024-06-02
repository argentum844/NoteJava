package service;

import dto.Users.Controller.*;
import dto.Users.View.UserInsertViewDTO;
import entity.Users;
import mapper.UserMapper;
import mapper.converter.ConvertString;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.UserExistRepository;
import repository.UsersRepository;
import treats.hashed.PasswordHashed;
import treats.updates.UpdateStatusUser;
import treats.updates.UserUpdateCreator;
import treats.validators.*;
import treats.validators.load.LoadValidationError;
import treats.validators.load.LoadValidationResult;
import treats.validators.logopass.LoginError;
import treats.validators.logopass.UpdatePasswordError;
import utils.ConnectionFactory;
import utils.exceptions.DBExceptions;
import utils.exceptions.ServiceException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class UserServiceTest {
//    private final DateValidator dateValidator = new DateValidator();
//    private final LoginValidator loginValidator = new LoginValidator();
//    private final NameValidator nameValidator = new NameValidator();
//    private final PasswordValidator passwordValidator = new PasswordValidator();

    private PasswordHashed passwordHashed;
    private UsersRepository usersRepository;
    private UpdateUserValidator updateUserValidator;
    private InsertUserValidator insertUserValidator;
    private UserService userService;
    private ResultSet resultSet;

    private final UserInsertControllerDTO userInsertControllerDTO = UserInsertControllerDTO.builder()
            .login("qwerty")
            .userPassword("Qwerty1234")
            .userName("ivan")
            .userSurname("Pupkin")
            .isAdmin(false)
            .birthdayDate(LocalDate.of(1996, 12, 7))
            .build();

    private final UserInsertControllerDTO uncorrectUserInsertControllerDTO = UserInsertControllerDTO.builder()
            .login("q")
            .userPassword("Q")
            .userName("ivan")
            .userSurname("Pupkin")
            .isAdmin(false)
            .birthdayDate(LocalDate.of(1996, 12, 7))
            .build();

    public final Users user = new Users("qwerty", "Qwerty1234", "ivan", "Pupkin", false,
            LocalDate.of(1996, 12, 7), "qwerty");


    private void clear() {
        try (Connection connection = new ConnectionFactory().getConnection()) {
            String CLEAR = "TRUNCATE Users RESTART IDENTITY CASCADE";
            connection.prepareStatement(CLEAR).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    private void init() {
        resultSet = mock(ResultSet.class);

        insertUserValidator = mock(InsertUserValidator.class);
        updateUserValidator = mock(UpdateUserValidator.class);
        usersRepository = mock(UsersRepository.class);
        passwordHashed = mock(PasswordHashed.class);
        userService = new UserService(passwordHashed, usersRepository, updateUserValidator, insertUserValidator);
    }

//    @BeforeEach
//    private void setup() {
//        clear();
//    }
//
//    @AfterEach
//    private void tearDown() {
//        clear();
//    }


    @Test
    void insertAndSelectCorrectUserTest() throws DBExceptions, ServiceException, SQLException {
        when(usersRepository.insert(user)).thenReturn(true);
        when(passwordHashed.generateSalt()).thenReturn(user.getSalt());
        when(passwordHashed.passwordHash(user.getUserPassword(), user.getSalt())).thenReturn(user.getUserPassword());

        userService.insertUser(userInsertControllerDTO);

        UserLoginControllerDTO dto = new UserLoginControllerDTO(userInsertControllerDTO.login(), userInsertControllerDTO.userPassword());

        when(resultSet.getLong("id_user")).thenReturn(user.getIdUser());
        when(resultSet.getString("login")).thenReturn(user.getLogin());
        when(resultSet.getString("user_name")).thenReturn(user.getUserName());
        when(resultSet.getString("user_surname")).thenReturn(user.getUserSurname());
        when(resultSet.getBoolean("is_admin")).thenReturn(user.getIsAdmin());
        when(resultSet.getDate("birtday_date")).thenReturn(ConvertString.dateToSQLDate(user.getBirthdayDate()));
        when(usersRepository.getByLogin(dto.login())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        UsersControllerDTO userSelect = userService.selectUser(dto);
        UsersControllerDTO userInsert = UsersControllerDTO.builder()
                .idUser(userSelect.idUser())
                .userName(userInsertControllerDTO.userName())
                .userSurname(userInsertControllerDTO.userSurname())
                .login(userInsertControllerDTO.login())
                .isAdmin(userInsertControllerDTO.isAdmin())
                .birthdayDate(userInsertControllerDTO.birthdayDate())
                .build();
        assertThat(userInsert.equals(userSelect));
    }

    @Test
    void insertUncorrectUserTest() throws DBExceptions {
        when(insertUserValidator.isValid(any(), eq(UserMapper.map(userInsertControllerDTO)))).thenThrow(new DBExceptions(""));
        assertThatThrownBy(() -> userService.insertUser(uncorrectUserInsertControllerDTO));
    }

    @Test
    void checkLoginNormalTest() throws DBExceptions, SQLException {
        userService.insertUser(userInsertControllerDTO);
        UserLoginControllerDTO normalLogin = new UserLoginControllerDTO(userInsertControllerDTO.login(),
                userInsertControllerDTO.userPassword());
        Optional<LoginError> res = userService.checkLogin(normalLogin);
        assertThat(res.isEmpty());
    }

    @Test
    void checkLoginUnrealTest() throws DBExceptions, SQLException {
        userService.insertUser(userInsertControllerDTO);
        UserLoginControllerDTO unreal_login = new UserLoginControllerDTO("123456", "1234567");
        Optional<LoginError> res = userService.checkLogin(unreal_login);
        assertThat(res.get().equals(LoginError.USER_NOT_EXISTS));
    }

    @Test
    void checkLoginUncorrectPasswordTest() throws DBExceptions, SQLException {
        userService.insertUser(userInsertControllerDTO);
        UserLoginControllerDTO uncorrect_password = new UserLoginControllerDTO(userInsertControllerDTO.login(), "123456");
        Optional<LoginError> res = userService.checkLogin(uncorrect_password);
        assertThat(res.get().equals(LoginError.PASSWORD_FAILED));
    }

    @Test
    void updateDescriptionUserTest() throws DBExceptions, ServiceException, SQLException {
        UserInsertControllerDTO insert = UserInsertControllerDTO.builder()
                .login(user.getLogin())
                .birthdayDate(user.getBirthdayDate())
                .isAdmin(user.getIsAdmin())
                .userSurname(user.getUserSurname())
                .userName(user.getUserName())
                .userPassword(user.getUserPassword())
                .build();
        userService.insertUser(insert);
        user.setIdUser(1);
        UserUpdateDescriptionControllerDTO dto = UserUpdateDescriptionControllerDTO.builder()
                .userName("vasya")
                .birthdayDate(LocalDate.of(2000, 1, 1))
                .idUser(user.getIdUser())
                .build();
        UpdateStatusUser status = UpdateStatusUser.builder()
                .isNewUserName(true)
                .isNewBirthdayDate(true)
                .build();
        userService.updateDescriptionUser(dto, status, new UserConstFieldsControllerDTO(user.getIdUser(), user.getIsAdmin()));
        UsersControllerDTO new_user = UsersControllerDTO.builder()
                .idUser(user.getIdUser())
                .login(user.getLogin())
                .userName("vasya")
                .birthdayDate(LocalDate.of(2000, 1, 1))
                .userSurname(user.getUserSurname())
                .isAdmin(user.getIsAdmin())
                .build();
        UsersControllerDTO selectedUser = userService.selectUser(new UserLoginControllerDTO(user.getLogin(), user.getUserPassword()));
        assertThat(new_user.equals(selectedUser));
    }

    @Test
    void updatePasswordTest() throws DBExceptions, ServiceException, SQLException {
        UserLogoPassControllerDTO logopass = UserLogoPassControllerDTO.builder()
                .login(user.getLogin())
                .oldPass(user.getUserPassword())
                .newPass("Qwerty12345")
                .build();
        UserInsertControllerDTO insert = UserInsertControllerDTO.builder()
                .login(user.getLogin())
                .birthdayDate(user.getBirthdayDate())
                .isAdmin(user.getIsAdmin())
                .userSurname(user.getUserSurname())
                .userName(user.getUserName())
                .userPassword(user.getUserPassword())
                .build();
        userService.insertUser(insert);
        Optional<UpdatePasswordError> error = userService.updatePasswordError(logopass);
        assertThat(error.isEmpty());
        UsersControllerDTO dto = userService.selectUser(new UserLoginControllerDTO(user.getLogin(), logopass.newPass()));
        assertThat(dto.login().equals(user.getLogin()));
    }

    @Test
    void deleteUserTest() throws DBExceptions, SQLException {
        userService.insertUser(userInsertControllerDTO);
        userService.deleteUser(1);
        List<UserForAdminControllerDTO> res = userService.selectAllUsers();
        assertThat(res.isEmpty());
    }

    @Test
    void selectAllUsersTest() throws DBExceptions, SQLException {
        userService.insertUser(userInsertControllerDTO);
        List<UserForAdminControllerDTO> res = userService.selectAllUsers();
        assertThat(res.get(0).equals(UserMapper.mapToAdminController(user)));
    }
}
