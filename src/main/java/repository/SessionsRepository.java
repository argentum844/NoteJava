package repository;

import entity.Sessions;
import mapper.converter.ConvertString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConnectionFactory;
import utils.exceptions.DBExceptions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionsRepository {
    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private static final Logger logger = LogManager.getLogger();

    public boolean insert(Sessions session) {
        try (Connection connection = connectionFactory.getConnection()) {
            String INSERT_BY_ID_SESSION_TEMPLATE = """
                    INSERT INTO Sessions(id_user, date_start, date_end)
                    VALUES (?, ?, ?)                            
                    """;
            PreparedStatement ps = connection.prepareStatement(INSERT_BY_ID_SESSION_TEMPLATE);
            ps.setLong(1, session.getIdUser());
            ps.setDate(2, ConvertString.dateToSQLDate(session.getDateStart()));
            ps.setDate(3, ConvertString.dateToSQLDate(session.getDateEnd()));
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("Failed insert session with id_user=" + session.getIdUser() + ". Error: " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAll(String filter) throws DBExceptions {
        ResultSet resultSet;
        try (Connection connection = connectionFactory.getConnection()) {
            String SELECT_ALL_TEMPLATE = """
                    SELECT id_session, id_user, date_start, date_end
                    FROM Sessions
                    WHERE 
                    """ + filter;
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_TEMPLATE);
            resultSet = ps.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            logger.error("Failed select sessions. Error: " + e.getLocalizedMessage());
            throw new DBExceptions(e.getClass().getSimpleName());
        }
    }

    public boolean delete(long id) throws DBExceptions {
        try (Connection connection = connectionFactory.getConnection()) {
            String DELETE_BY_ID_TEMPLATE = """
                    DELETE FROM Sessions WHERE id_session = ?
                    """;
            PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID_TEMPLATE);
            ps.setLong(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("Failed delete session with id=" + id + ". Error: " + e.getLocalizedMessage());
            throw new DBExceptions(e.getClass().getSimpleName());
        }
    }

    public ResultSet getByID(long id) throws DBExceptions {
        ResultSet resultSet;
        try (Connection connection = connectionFactory.getConnection()) {
            String SELECT_BY_ID_TEMPLATE = """
                    SELECT id_session, id_user, date_start, date_end
                    FROM Sessions
                    WHERE id_session = ?
                    """;
            PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID_TEMPLATE);
            ps.setLong(1, id);
            resultSet = ps.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            logger.error("Failed select session with id=" + id + ". Error: " + e.getLocalizedMessage());
            throw new DBExceptions(e.getClass().getSimpleName());
        }
    }

    public void update(Sessions session) throws SQLException, DBExceptions {
        Connection connection = null;
        try {
            connection = connectionFactory.getConnection();
            String UPDATE_TEMPLATE = """
                    UPDATE Sessions SET (id_user, date_start, date_end) = (?, ?, ?)
                    WHERE id_session = ?
                    """;
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(UPDATE_TEMPLATE);
            ps.setLong(1, session.getIdUser());
            ps.setDate(2, ConvertString.dateToSQLDate(session.getDateStart()));
            ps.setDate(3, ConvertString.dateToSQLDate(session.getDateEnd()));
            ps.setLong(4, session.getIdSession());
            ps.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            logger.error("Failed update session with id=" + session.getIdSession() + ". Error: " + e.getLocalizedMessage());
            throw new DBExceptions(e.getClass().getSimpleName());
        }
    }
}
