package managers;

import treats.validators.*;

public class ValidationManager {
    public UpdateUserValidator updateUserValidator = new UpdateUserValidator(
            new DateValidator(), new LoginValidator(), new NameValidator(), new PasswordValidator());
    public InsertUserValidator insertUserValidator = new InsertUserValidator(new DateValidator(), new LoginValidator(),
            new NameValidator(), new PasswordValidator());
}
