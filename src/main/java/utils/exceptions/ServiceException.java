package utils.exceptions;

public class ServiceException extends Exception{
    private String message;

    public ServiceException(String ex) {
        switch (ex) {
            case "UserNotFound"->message="Ошибка - пользователь не найден";
            case "UpdateFailed"->message="Ошибка - не удалось обновить данные";
            default -> message = "Ошибка в работе с сервисом";
        }
    }

    @Override
    public String getMessage() {
        return message;
    }
}
