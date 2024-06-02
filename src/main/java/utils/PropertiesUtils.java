package utils;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtils {
    private PropertiesUtils() {

    }

    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (var stream = PropertiesUtils.class.getClassLoader().getResourceAsStream("db.properties")) {
            PROPERTIES.load(stream);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
