package repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConnectionFactory;
import utils.exceptions.DBExceptions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserExistRepository {
    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private static final Logger logger = LogManager.getLogger();

    public boolean getByLogin(String login) throws DBExceptions {
        try (Connection connection = connectionFactory.getConnection()) {
            String SELECT_BY_LOGIN = "SELECT 1 FROM Users WHERE login = ?";
            PreparedStatement ps = connection.prepareStatement(SELECT_BY_LOGIN);
            ps.setString(1, login);
            try (ResultSet res = ps.executeQuery()) {
                return res.next();
            }
        } catch (SQLException e) {
            logger.error("Failed select user with login=" + login + ". Error: " + e.getLocalizedMessage());
            throw new DBExceptions(e.getClass().getSimpleName());
        }
    }
}
