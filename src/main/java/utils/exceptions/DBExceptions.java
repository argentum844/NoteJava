package utils.exceptions;


public class DBExceptions extends Exception {
    private String message;

    public DBExceptions(String ex) {
        if (ex.equals("BatchUpdateException")) {
            message = "Ошибка во время группового запроса";
        }
        switch (ex) {
            case "BatchUpdateException" -> message = "Ошибка во время группового запроса";
            case "SQLSyntaxErrorException" -> message = "Ошибка в синтаксисе запроса";
            case "SQLTransactionRollbackException" -> message = "Ошибка во время отката транзакции";
            case "SQLTimeoutException" -> message = "Запрос выполнялся слишком долго";
            default -> message = "Ошибка при работе с БД";
        }
    }

    @Override
    public String getMessage() {
        return message;
    }
}
