package repository;

import entity.Users;
import mapper.converter.ResultSetToUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import treats.updates.UpdateStatusUser;
import treats.updates.UserUpdateCreator;
import utils.ConnectionFactory;
import utils.exceptions.DBExceptions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserRepositoryTest {
    private final UserUpdateCreator userUpdateCreator = new UserUpdateCreator();
    private final UsersRepository userRepository = new UsersRepository(userUpdateCreator);
    private final UpdateStatusUser updateStatusUserAll = UpdateStatusUser.builder()
            .isNewLogin(true)
            .isNewUserName(true)
            .isNewUserSurname(true)
            .isNewBirthdayDate(true)
            .isNewIsAdmin(true)
            .build();

    private final UpdateStatusUser updateStatusUserNothing = UpdateStatusUser.builder()
            .isNewLogin(false)
            .isNewUserName(false)
            .isNewUserSurname(false)
            .isNewBirthdayDate(false)
            .isNewIsAdmin(false)
            .build();

    private final UpdateStatusUser updateStatusUserLogin = UpdateStatusUser.builder()
            .isNewLogin(true)
            .isNewUserName(false)
            .isNewUserSurname(false)
            .isNewBirthdayDate(false)
            .isNewIsAdmin(false)
            .build();

    public static Stream<Users> generateValidUser() {
        Users user1 = new Users("user1", "password1", "Иван", "Иванов", false,
                LocalDate.of(1995, 1, 21), "salt1");
        Users user2 = new Users("user2", "password2", "Вася", "Сидоров", false,
                LocalDate.of(2005, 10, 2), "salt2");
        return Stream.of(user1, user2);
    }

    public static Stream<Users> generateInvalidUser() {
        Users user1 = new Users(null, "password1", "Иван", "Иванов", false,
                LocalDate.of(1995, 1, 21), "salt1");
        Users user2 = new Users("user2", null, "Вася", "Сидоров", false,
                LocalDate.of(2005, 10, 2), "");
        Users user3 = new Users("user3", "password3", "123", "Сидоров", false,
                LocalDate.of(2005, 10, 2), null);
        return Stream.of(user1, user2, user3);
    }

    private Users defaultUser() {
        return new Users("user1", "password1", "Вася", "Сидоров", false,
                LocalDate.of(2005, 10, 2), "salt1");
    }

    private void settingsToDefault(Users user) {
        user.setUserPassword(null);
        user.setSalt(null);
        user.setIdUser(0);
    }

    private void clear() {
        try (Connection connection = new ConnectionFactory().getConnection()) {
            String CLEAR = "TRUNCATE Users RESTART IDENTITY CASCADE";
            connection.prepareStatement(CLEAR).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    private void setup() {
        clear();
    }

    @AfterEach
    private void tearDown() {
        clear();
    }

    @Test
    void insertValidUserTest() throws DBExceptions, SQLException {
        Users user = defaultUser();
        userRepository.insert(user);
        settingsToDefault(user);

        Optional<Users> userOptionalLogin = ResultSetToUser.resultSetToOptional(userRepository.getByLogin(user.getLogin()));
        assertThat(userOptionalLogin.isPresent()).isTrue();
        Users userFromRepository = userOptionalLogin.get();
        assertThat(userFromRepository).isEqualTo(user);
    }

    @Test
    void insertInvalidUserTest() {
        generateInvalidUser().forEach((Users user) -> {
            assertThatThrownBy(() -> userRepository.insert(user)).isInstanceOf(RuntimeException.class);
        });
    }

    @Test
    void selectAllUsersTest() throws SQLException {
        List<Users> list = generateValidUser().toList();
        for (var user : list) {
            userRepository.insert(user);
        }
        List<Users> listFromRepository = new ArrayList<>();
        ResultSet resultSet = userRepository.getAll();
        clear();
        while (resultSet.next()) {
            listFromRepository.add(ResultSetToUser.convert(resultSet));
        }
        assertThat(listFromRepository).isEqualTo(list);

    }

    @Test
    void selectByValidLoginTest() throws DBExceptions, SQLException {
        Users user = defaultUser();
        userRepository.insert(user);
        settingsToDefault(user);
        Optional<Users> usersOptional = ResultSetToUser.resultSetToOptional(userRepository.getByLogin(user.getLogin()));
        assertThat(usersOptional.isPresent()).isTrue();
        Users userFromRepository = usersOptional.get();
        assertThat(userFromRepository).isEqualTo(user);
    }

    @Test
    void selectByInvalidLoginTest() throws DBExceptions, SQLException {
        String login = "!!!";
        Users user = defaultUser();
        userRepository.insert(user);

        Optional<Users> usersOptional = ResultSetToUser.resultSetToOptional(userRepository.getByLogin(login));
        assertThat(usersOptional.isPresent()).isFalse();
    }

    @Test
    void updateAllTest() throws DBExceptions, SQLException {
        Users user = defaultUser();
        userRepository.insert(user);
        user.setUserName("Мария");
        user.setLogin("user_new");
        user.setUserSurname("Иванова");
        user.setBirthdayDate(LocalDate.of(2000, 2, 2));
        userRepository.update(user);

        settingsToDefault(user);

        Optional<Users> usersOptional = ResultSetToUser.resultSetToOptional(userRepository.getByLogin(user.getLogin()));
        assertThat(usersOptional.isPresent()).isTrue();
        Users userFromRepository = usersOptional.get();
        assertThat(userFromRepository).isEqualTo(user);
    }

    @Test
    void updateDescriptionTest() throws DBExceptions, SQLException {
        Users user = defaultUser();
        userRepository.insert(user);
        user.setUserName("Мария");
        user.setLogin("user_new");
        user.setUserSurname("Иванова");
        user.setBirthdayDate(LocalDate.of(2000, 2, 2));
        UpdateStatusUser updateStatusUser = UpdateStatusUser.builder()
                .isNewUserName(true)
                .isNewLogin(true)
                .isNewUserSurname(true)
                .isNewBirthdayDate(true)
                .build();
        userRepository.updateDescriptionCreation(user, updateStatusUser);

        settingsToDefault(user);

        Optional<Users> usersOptional = ResultSetToUser.resultSetToOptional(userRepository.getByLogin(user.getLogin()));
        assertThat(usersOptional.isPresent()).isTrue();
        Users userFromRepository = usersOptional.get();
        assertThat(userFromRepository).isEqualTo(user);
    }

    @Test
    void updatePasswordTest() throws DBExceptions, SQLException {
        Users user = defaultUser();
        userRepository.insert(user);
        user.setUserPassword("password50");
        user.setSalt("salt50");
        userRepository.updatePassword(user);

        ResultSet rs = userRepository.getByLogin(user.getLogin());
        rs.next();
        Optional<Users> usersOptional = Optional.ofNullable(ResultSetToUser.convert(rs));
        assertThat(usersOptional.isPresent()).isTrue();
        Users userFromRepository = usersOptional.get();
        assertThat(userFromRepository).isEqualTo(user);
    }

    @Test
    void deleteByIDTest() throws DBExceptions, SQLException {
        Users user = defaultUser();
        userRepository.insert(user);
        userRepository.delete(user.getIdUser());

        settingsToDefault(user);

        Optional<Users> usersOptional = ResultSetToUser.resultSetToOptional(userRepository.getByLogin(user.getLogin()));
        assertThat(usersOptional.isPresent()).isFalse();
    }
}
