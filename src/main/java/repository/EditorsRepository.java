package repository;

import entity.Editors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.ConnectionFactory;
import utils.exceptions.DBExceptions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditorsRepository {
    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private static final Logger logger = LogManager.getLogger();

    public boolean insert(Editors editor) {
        try (Connection connection = connectionFactory.getConnection()) {
            String INSERT_BY_ID_EDITOR_TEMPLATE =
                    """
                            INSERT INTO Editors(id_document, id_editor)
                            VALUES (?,?)                            
                            """;
            PreparedStatement ps = connection.prepareStatement(INSERT_BY_ID_EDITOR_TEMPLATE);
            ps.setLong(1, editor.getIdDocument());
            ps.setLong(2, editor.getIdDocument());
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("Failed insert editor with id_editor=" + editor.getIdEditor() + ". Error: " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAll(String filter) throws DBExceptions {
        ResultSet resultSet;
        try (Connection connection = connectionFactory.getConnection()) {
            String SELECT_ALL_TEMPLATE =
                    """
                            SELECT id_document, id_editor
                            FROM Editors
                            WHERE 
                            """ + filter;
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_TEMPLATE);
            resultSet = ps.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            logger.error("Failed select editors. Error: " + e.getLocalizedMessage());
            throw new DBExceptions(e.getClass().getSimpleName());
        }
    }

    public boolean delete(long id_document, long id_editor) throws DBExceptions {
        try (Connection connection = connectionFactory.getConnection()) {
            String DELETE_BY_ID_TEMPLATE =
                    """
                            DELETE FROM Editors WHERE id_editor = ? AND id_document=?
                            """;
            PreparedStatement ps = connection.prepareStatement(DELETE_BY_ID_TEMPLATE);
            ps.setLong(1, id_editor);
            ps.setLong(2, id_document);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            logger.error("Failed delete editor with id_editor=" + id_editor + ". Error: " + e.getLocalizedMessage());
            throw new DBExceptions(e.getClass().getSimpleName());
        }
    }

}
