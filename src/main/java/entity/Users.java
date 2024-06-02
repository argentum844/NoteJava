package entity;


import java.time.LocalDate;
import java.util.Objects;

public class Users {
    private long idUser;
    private String login;
    private String userPassword;
    private String userName;
    private String userSurname;
    private boolean isAdmin;
    private LocalDate birthdayDate;
    private String salt;

    public Users(){}

    public Users(long idUser, String login, String userPassword, String userName,
          String userSurname, boolean isAdmin, LocalDate birthdayDate, String salt) {
        this.idUser = idUser;
        this.login = login;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userSurname = userSurname;
        this.isAdmin = isAdmin;
        this.birthdayDate = birthdayDate;
        this.salt = salt;
    }
    public Users(long idUser, String login, String userName,
          String userSurname, boolean isAdmin, LocalDate birthdayDate) {
        this.idUser = idUser;
        this.login = login;
        this.userName = userName;
        this.userSurname = userSurname;
        this.isAdmin = isAdmin;
        this.birthdayDate = birthdayDate;
    }

    public Users(String login, String userPassword, String userName,
                 String userSurname, boolean isAdmin, LocalDate birthdayDate, String salt) {
        this.login = login;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userSurname = userSurname;
        this.isAdmin = isAdmin;
        this.birthdayDate = birthdayDate;
        this.salt = salt;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(LocalDate birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "Users{" +
                "idUser=" + idUser +
                ", login='" + login + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userName='" + userName + '\'' +
                ", userSurname='" + userSurname + '\'' +
                ", isAdmin=" + isAdmin +
                ", birthdayDate=" + birthdayDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return isAdmin == users.isAdmin
                && Objects.equals(login, users.login)
                && Objects.equals(userPassword, users.userPassword)
                && Objects.equals(userName, users.userName)
                && Objects.equals(userSurname, users.userSurname)
                && Objects.equals(birthdayDate, users.birthdayDate)
                && Objects.equals(salt, users.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, userPassword, userName, userSurname, isAdmin, birthdayDate, salt);
    }
}
