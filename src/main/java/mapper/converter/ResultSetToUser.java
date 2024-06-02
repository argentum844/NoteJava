package mapper.converter;

import entity.Users;
import utils.exceptions.DBExceptions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class ResultSetToUser {
    public static Users convertWithoutPassword(ResultSet res) throws SQLException {
        long id = res.getLong("id_user");
        String login = res.getString("login");
        String userName = res.getString("user_name");
        String userSurname = res.getString("user_surname");
        boolean isAdmin = res.getBoolean("is_admin");
        LocalDate birthdayDate = res.getDate("birtday_date").toLocalDate();
        if (login == null) {
            return null;
        } else {
            return new Users(id, login, userName, userSurname, isAdmin, birthdayDate);
        }
    }

    public static Users convert(ResultSet res) throws SQLException {
        String password = res.getString("user_password");
        String salt = res.getString("salt");
        Users user = ResultSetToUser.convertWithoutPassword(res);
        if (user != null) {
            user.setUserPassword(password);
            user.setSalt(salt);
            return user;
        } else {
            return null;
        }
    }

    public static Optional<Users> resultSetToOptional(ResultSet res) throws SQLException {
        if (res.next()) {
            return Optional.ofNullable(convertWithoutPassword(res));
        } else {
            return Optional.ofNullable(null);
        }
    }
}
