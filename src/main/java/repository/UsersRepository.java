package repository;

import entity.Users;
import mapper.converter.ConvertString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import treats.updates.UpdateStatusUser;
import treats.updates.UserUpdateCreator;
import utils.ConnectionFactory;
import utils.exceptions.DBExceptions;

import java.sql.*;

public class UsersRepository {
    private static final Logger logger = LogManager.getLogger();
    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private final UserUpdateCreator userUpdateCreator;

    public UsersRepository(UserUpdateCreator userUpdateCreator) {
        this.userUpdateCreator = userUpdateCreator;
    }

    public boolean insert(Users user) {
        try (Connection connection = connectionFactory.getConnection()) {
            String INSERT_BY_ID_USER_TEMPLATE = """
                    INSERT INTO Users(login, user_password, user_name, user_surname, is_admin, birtday_date, salt)
                    VALUES (?, ?, ?, ?, ?, ?,?)                            
                    """;
            PreparedStatement ps = connection.prepareStatement(INSERT_BY_ID_USER_TEMPLATE, Statement.RETURN_GENERATED_KEYS);
            if (user.getLogin() == null || user.getLogin().isEmpty())
                throw new DBExceptions("");
            ps.setString(1, user.getLogin());
            if (user.getUserPassword() == null || user.getUserPassword().isEmpty())
                throw new DBExceptions("");
            ps.setString(2, user.getUserPassword());
            ps.setString(3, user.getUserName());
            ps.setString(4, user.getUserSurname());
            ps.setBoolean(5, user.getIsAdmin());
            ps.setDate(6, ConvertString.dateToSQLDate(user.getBirthdayDate()));
            if (user.getSalt() == null || user.getSalt().isEmpty())
                throw new DBExceptions("");
            ps.setString(7, user.getSalt());
            boolean res = ps.executeUpdate() == 1;
            var keys = ps.getGeneratedKeys();
            if (keys.next()) {
                user.setIdUser(keys.getLong("id_user"));
            }
            return res;
        } catch (SQLException | DBExceptions e) {
            logger.error("Failed insert user with login=" + user.getLogin() + ". Error: " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAll(String filter) throws DBExceptions {
        ResultSet resultSet;
        try (Connection connection = connectionFactory.getConnection()) {
            String SELECT_ALL_TEMPLATE = """
                    SELECT id_user, login, user_password, user_name, user_surname, is_admin, birtday_date, salt
                    FROM Users
                    WHERE 
                    """ + filter;
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_TEMPLATE);
            resultSet = ps.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            logger.error("Failed select users. Error: " + e.getLocalizedMessage());
            throw new DBExceptions(e.getClass().getSimpleName());
        }
    }

    public ResultSet getAll(){
        ResultSet resultSet;
        try (Connection connection = connectionFactory.getConnection()) {
            String SELECT_ALL_TEMPLATE = """
                    SELECT id_user, login, user_password, user_name, user_surname, is_admin, birtday_date, salt
                    FROM Users
                    """;
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_TEMPLATE);
            resultSet = ps.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            logger.error("Failed select users. Error: " + e.getLocalizedMessage());
            throw new RuntimeException(e.getClass().getSimpleName());
        }
    }

    public boolean delete(long id) throws DBExceptions {
        try (Connection connection = connectionFactory.getConnection()) {
            String DELETE_BY_ID_TEMPLATE = """
                    DELETE FROM Users WHERE id_user = ?
                    """;
            PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID_TEMPLATE);
            ps.setLong(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("Failed delete user with id=" + id + ". Error: " + e.getLocalizedMessage());
            throw new DBExceptions(e.getClass().getSimpleName());
        }
    }

    public ResultSet getByLogin(String login) throws DBExceptions {
        ResultSet resultSet;
        try (Connection connection = connectionFactory.getConnection()) {
            String SELECT_BY_ID_TEMPLATE = """
                    SELECT id_user, login, user_password, user_name, user_surname, is_admin, birtday_date, salt
                    FROM Users
                    WHERE login = ?
                    """;
            PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID_TEMPLATE);
            ps.setString(1, login);
            resultSet = ps.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            logger.error("Failed select user with login=" + login + ". Error: " + e.getLocalizedMessage());
            throw new DBExceptions(e.getClass().getSimpleName());
        }
    }

    public void update(Users user) throws SQLException, DBExceptions {
        Connection connection = null;
        try {
            connection = connectionFactory.getConnection();
            String UPDATE_TEMPLATE = """
                    UPDATE Users 
                    SET (login, user_password, user_name, user_surname, is_admin, birtday_date, salt) = (?,?,?,?,?,?,?)
                    WHERE id_user = ?
                    """;
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(UPDATE_TEMPLATE);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getUserPassword());
            ps.setString(3, user.getUserName());
            ps.setString(4, user.getUserSurname());
            ps.setBoolean(5, user.getIsAdmin());
            ps.setDate(6, ConvertString.dateToSQLDate(user.getBirthdayDate()));
            ps.setString(7, user.getSalt());
            ps.setLong(8, user.getIdUser());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            logger.error("Failed update user with id=" + user.getIdUser() + ". Error: " + e.getLocalizedMessage());
            throw new DBExceptions(e.getClass().getSimpleName());
        }
    }

    public boolean updateDescriptionCreation(Users user, UpdateStatusUser status) throws DBExceptions {
        String sql = userUpdateCreator.createUserUpdate(user, status);
        if (!sql.equals("empty")) {
            try {
                Connection connection = connectionFactory.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                logger.error("Fail to update user with id = " + user.getIdUser() + ". Error: " + e.getLocalizedMessage());
                throw new DBExceptions(e.getClass().getSimpleName());
            }
        }
        return true;
    }

    public boolean updatePassword(Users user) throws DBExceptions {
        try (Connection connection = connectionFactory.getConnection()) {
            String UPDATE_PASSWORD = "UPDATE Users SET user_password=?, salt=? WHERE login=?";
            PreparedStatement ps = connection.prepareStatement(UPDATE_PASSWORD);
            ps.setString(1, user.getUserPassword());
            ps.setString(2, user.getSalt());
            ps.setString(3, user.getLogin());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Fail to update user password: " + user.toString() + ". Error: " + e.getLocalizedMessage());
            throw new DBExceptions(e.getClass().getSimpleName());
        }
    }

}
