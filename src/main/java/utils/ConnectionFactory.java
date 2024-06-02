package utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionFactory {
    private static final String URL = "db.url";
    private static final String USERNAME = "db.username";
    private static final String PASSWORD = "db.password";
    private static final String DRIVER = "db.driver";

    public Connection getConnection() {
        try {
            Class.forName(PropertiesUtils.get(DRIVER));
            return DriverManager.getConnection(PropertiesUtils.get(URL),
                    PropertiesUtils.get(USERNAME),
                    PropertiesUtils.get(PASSWORD));
        } catch (SQLException | ClassNotFoundException ex) {

            throw new RuntimeException(ex);
        }
    }
}
