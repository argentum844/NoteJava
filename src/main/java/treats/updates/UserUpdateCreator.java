package treats.updates;

import entity.Users;
import mapper.converter.ConvertString;

public class UserUpdateCreator {
    public String createUserUpdate(Users user, UpdateStatusUser update) {
        String start = "UPDATE Users SET ";
        StringBuilder sql = new StringBuilder(start);

        if (update.isNewLogin()) {
            sql.append("login = '").append(user.getLogin()).append("', ");
        }
        if (update.isNewUserName()) {
            sql.append("user_name = '").append(user.getUserName()).append("', ");
        }
        if (update.isNewUserSurname()) {
            sql.append("user_surname = '").append(user.getUserSurname()).append("', ");
        }
        if (update.isNewIsAdmin()) {
            sql.append("is_admin = '").append(user.getIsAdmin()).append("', ");
        }
        if (update.isNewBirthdayDate()) {
            sql.append("birtday_date = '").append(ConvertString.dateToString(user.getBirthdayDate())).append("', ");
        }
        if (sql.toString().endsWith(", ")) {
            sql.setLength(sql.length() - 2);
        } else {
            return "empty";
        }

        sql.append("WHERE id_user = ").append(user.getIdUser()).append(" ;");
        return sql.toString();
    }
}
