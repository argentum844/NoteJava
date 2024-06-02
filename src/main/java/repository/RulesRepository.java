package repository;

import entity.Rules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConnectionFactory;
import utils.exceptions.DBExceptions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RulesRepository {
    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private static final Logger logger = LogManager.getLogger();

    public boolean insert(Rules rules) {
        try (Connection connection = connectionFactory.getConnection()) {
            String INSERT_BY_ID_RULES_TEMPLATE = """
                    INSERT INTO Rules(text_rules)
                    VALUES (?)                            
                    """;
            PreparedStatement ps = connection.prepareStatement(INSERT_BY_ID_RULES_TEMPLATE);
            ps.setString(1, rules.getTextRules());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("Failed insert rule with text=" + rules.getTextRules() + ". Error: " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAll(String filter) throws DBExceptions {
        ResultSet resultSet;
        try (Connection connection = connectionFactory.getConnection()) {
            String SELECT_ALL_TEMPLATE = """
                    SELECT id_rules, text_rules
                    FROM Rules
                    WHERE
                    """ + filter;
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_TEMPLATE);
            resultSet = ps.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            logger.error("Failed select rules. Error: " + e.getLocalizedMessage());
            throw new DBExceptions(e.getClass().getSimpleName());
        }
    }

    public boolean delete(long id) throws DBExceptions {
        try (Connection connection = connectionFactory.getConnection()) {
            String DELETE_BY_ID_TEMPLATE = """
                    DELETE FROM Rules WHERE id_rules = ?
                    """;
            PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID_TEMPLATE);
            ps.setLong(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("Failed delete rule with id=" + id + ". Error: " + e.getLocalizedMessage());
            throw new DBExceptions(e.getClass().getSimpleName());
        }
    }

    public ResultSet getByID(long id) throws DBExceptions {
        ResultSet resultSet;
        try (Connection connection = connectionFactory.getConnection()) {
            String SELECT_BY_ID_TEMPLATE = """
                    SELECT id_rules, text_rules
                    FROM Rules
                    WHERE id_rules = ?
                    """;
            PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID_TEMPLATE);
            ps.setLong(1, id);
            resultSet = ps.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            logger.error("Failed select rule with id=" + id + ". Error: " + e.getLocalizedMessage());
            throw new DBExceptions(e.getClass().getSimpleName());
        }
    }

    public void update(Rules rules) throws SQLException, DBExceptions {
        Connection connection = null;
        try {
            connection = connectionFactory.getConnection();
            String UPDATE_TEMPLATE = """
                    UPDATE Rules SET text_rules = ?
                    WHERE id_rules = ?
                    """;
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(UPDATE_TEMPLATE);
            ps.setString(1, rules.getTextRules());
            ps.setLong(2, rules.getIdRules());
            ps.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            logger.error("Failed update rule with id=" + rules.getIdRules() + ". Error: " + e.getLocalizedMessage());
            throw new DBExceptions(e.getClass().getSimpleName());
        }
    }
}
