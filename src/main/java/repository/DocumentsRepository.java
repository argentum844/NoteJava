package repository;

import entity.Documents;
import mapper.converter.ConvertString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConnectionFactory;
import utils.exceptions.DBExceptions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DocumentsRepository {
    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private static final Logger logger = LogManager.getLogger();

    public boolean insert(Documents document) {
        try (Connection connection = connectionFactory.getConnection()) {
            String INSERT_BY_ID_DOCUMENTS_TEMPLATE = """
                    INSERT INTO Documents(id_author, author_displayed, title, description, linc_text_document, date_create, is_public)
                    VALUES (?,?,?,?,?,?,?)                            
                    """;
            PreparedStatement ps = connection.prepareStatement(INSERT_BY_ID_DOCUMENTS_TEMPLATE);
            ps.setLong(1, document.getIdAuthor());
            ps.setString(2, document.getAuthorDisplayed());
            ps.setString(3, document.getTitle());
            ps.setString(4, document.getDescription());
            ps.setString(5, document.getLincTextDocument());
            ps.setDate(6, ConvertString.dateToSQLDate(document.getDateCreate()));
            ps.setBoolean(7, document.isPublic());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("Failed insert document with title=" + document.getTitle() + ". Error: " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAll(String filter) throws DBExceptions {
        ResultSet resultSet;
        try (Connection connection = connectionFactory.getConnection()) {
            String SELECT_ALL_TEMPLATE = """
                    SELECT id_document, id_author, author_displayed, title, description, linc_text_document, date_create, is_public
                    FROM Documents
                    WHERE 
                    """ + filter;
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_TEMPLATE);
            resultSet = ps.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            logger.error("Failed select documents. Error: " + e.getLocalizedMessage());
            throw new DBExceptions(e.getClass().getSimpleName());
        }
    }

    public boolean delete(long id) throws DBExceptions {
        try (Connection connection = connectionFactory.getConnection()) {
            String DELETE_BY_ID_TEMPLATE = """
                    DELETE FROM Documents WHERE id_document = ?
                    """;
            PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID_TEMPLATE);
            ps.setLong(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("Failed delete document with id=" + id + ". Error: " + e.getLocalizedMessage());
            throw new DBExceptions(e.getClass().getSimpleName());
        }
    }

    public ResultSet getByID(long id) throws DBExceptions {
        ResultSet resultSet;
        try (Connection connection = connectionFactory.getConnection()) {
            String SELECT_BY_ID_TEMPLATE = """
                    SELECT id_document, id_author, author_displayed, title, description, linc_text_document, date_create, is_public
                    FROM Documents
                    WHERE id_document = ?
                    """;
            PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID_TEMPLATE);
            ps.setLong(1, id);
            resultSet = ps.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            logger.error("Failed select document with id=" + id + ". Error: " + e.getLocalizedMessage());
            throw new DBExceptions(e.getClass().getSimpleName());
        }
    }

    public void update(Documents document) throws SQLException, DBExceptions {
        Connection connection = null;
        try {
            connection = connectionFactory.getConnection();
            String UPDATE_TEMPLATE = """
                    UPDATE Documents 
                    SET (id_author, author_displayed, title, description, linc_text_document, date_create, is_public) = (?,?,?,?,?,?,?)
                    WHERE id_document = ?
                    """;
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement(UPDATE_TEMPLATE);
            ps.setLong(1, document.getIdAuthor());
            ps.setString(2, document.getAuthorDisplayed());
            ps.setString(3, document.getTitle());
            ps.setString(4, document.getDescription());
            ps.setString(5, document.getLincTextDocument());
            ps.setDate(6, ConvertString.dateToSQLDate(document.getDateCreate()));
            ps.setBoolean(7, document.isPublic());
            ps.setLong(8, document.getIdDocument());
            ps.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            logger.error("Failed update document with title=" + document.getTitle() + ". Error: " + e.getLocalizedMessage());
            throw new DBExceptions(e.getClass().getSimpleName());
        }
    }
}
