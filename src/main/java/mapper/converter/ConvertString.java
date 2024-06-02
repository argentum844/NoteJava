package mapper.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConvertString {
    public static boolean toBoolean(String str) {
        if (str == null)
            return false;
        return str.equals("true");
    }

    public static LocalDate toDate(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(str, formatter);
    }

    public static String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(date);
    }

    public static java.sql.Date dateToSQLDate(LocalDate date) {
        return java.sql.Date.valueOf(date);
    }

}
