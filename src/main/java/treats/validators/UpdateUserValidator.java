package treats.validators;

import dto.Users.View.UserUpdateDescriptionViewDTO;
import repository.UserExistRepository;
import treats.updates.UpdateStatusUser;
import treats.validators.load.LoadValidationError;
import treats.validators.load.LoadValidationResult;
import treats.validators.load.LoadValidationTypeError;
import utils.exceptions.DBExceptions;

public class UpdateUserValidator {
    private final DateValidator dateValidator;
    private final LoginValidator loginValidator;
    private final NameValidator nameValidator;
    private final PasswordValidator passwordValidator;

    public UpdateUserValidator(DateValidator dateValidator,
                               LoginValidator loginValidator, NameValidator nameValidator, PasswordValidator passwordValidator) {
        this.dateValidator = dateValidator;
        this.loginValidator = loginValidator;
        this.nameValidator = nameValidator;
        this.passwordValidator = passwordValidator;
    }

    public LoadValidationResult isValidDescription(UserExistRepository userExistRepository,
                                                   UserUpdateDescriptionViewDTO user, UpdateStatusUser update) throws DBExceptions {
        LoadValidationResult result = new LoadValidationResult();

        if (update.isNewLogin()) {
            if (user.login().trim().isEmpty()) {
                result.add(LoadValidationError.of("login", LoadValidationTypeError.EMPTY));
            } else if (userExistRepository.getByLogin(user.login())) {
                result.add(LoadValidationError.of("login", LoadValidationTypeError.NOT_UNIQUE));
            } else if (!loginValidator.isNormal(user.login())) {
                result.add(LoadValidationError.of("login", LoadValidationTypeError.INCORRECT));
            }
        }
        if (update.isNewBirthdayDate()) {
            if (user.birthdayDate().trim().isEmpty()) {
                result.add(LoadValidationError.of("birthdayDate", LoadValidationTypeError.EMPTY));
            } else if (!dateValidator.isNormal(user.birthdayDate())) {
                result.add(LoadValidationError.of("birthdayDate", LoadValidationTypeError.INCORRECT));
            }
        }
        if (update.isNewIsAdmin()) {
            if (user.isAdmin().trim().isEmpty()) {
                result.add(LoadValidationError.of("isAdmin", LoadValidationTypeError.EMPTY));
            }
        }
        if (update.isNewUserName()){
            if (user.userName() == null || user.userName().trim().isEmpty()) {
                result.add(LoadValidationError.of("userName", LoadValidationTypeError.EMPTY));
            } else if (!nameValidator.isNormal(user.userName())) {
                result.add(LoadValidationError.of("userName", LoadValidationTypeError.INCORRECT));
            }
        }
        if (update.isNewUserSurname()){
            if (user.userSurname() == null || user.userSurname().trim().isEmpty()) {
                result.add(LoadValidationError.of("userSurname", LoadValidationTypeError.EMPTY));
            } else if (!nameValidator.isNormal(user.userSurname())) {
                result.add(LoadValidationError.of("userSurname", LoadValidationTypeError.INCORRECT));
            }
        }
        return result;
    }

    public boolean isValidPassword(String password){
        return password != null && !password.trim().isEmpty() && passwordValidator.isNormal(password);
    }
}
