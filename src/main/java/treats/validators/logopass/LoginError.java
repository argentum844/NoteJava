package treats.validators.logopass;

public enum LoginError {
    USER_NOT_EXISTS("Пользователя с таким логином не существует!"),
    PASSWORD_FAILED("Пароль неправильный!");
    private final String type;

    LoginError(String type){
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
