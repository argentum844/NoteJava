package treats.validators;

import dto.Users.View.UserInsertViewDTO;
import repository.UserExistRepository;
import treats.validators.load.LoadValidationError;
import treats.validators.load.LoadValidationResult;
import treats.validators.load.LoadValidationTypeError;
import utils.exceptions.DBExceptions;
public class InsertUserValidator {
    private final DateValidator dateValidator;
    private final LoginValidator loginValidator;
    private final NameValidator nameValidator;
    private final PasswordValidator passwordValidator;

    public InsertUserValidator(DateValidator dateValidator,
                               LoginValidator loginValidator, NameValidator nameValidator, PasswordValidator passwordValidator){
        this.dateValidator = dateValidator;
        this.loginValidator = loginValidator;
        this.nameValidator = nameValidator;
        this.passwordValidator = passwordValidator;
    }

    public LoadValidationResult isValid(UserExistRepository userExistRepository, UserInsertViewDTO user) throws DBExceptions {
        LoadValidationResult result = new LoadValidationResult();

        if (user.login() == null || user.login().trim().isEmpty()){
            result.add(LoadValidationError.of("login", LoadValidationTypeError.EMPTY));
        } else if (userExistRepository.getByLogin(user.login())){
            result.add(LoadValidationError.of("login", LoadValidationTypeError.NOT_UNIQUE));
        } else if (!loginValidator.isNormal(user.login())){
            result.add(LoadValidationError.of("login", LoadValidationTypeError.INCORRECT));
        }

        if (user.userPassword() == null || user.userPassword().trim().isEmpty()) {
            result.add(LoadValidationError.of("userPassword", LoadValidationTypeError.EMPTY));
        } else if (!passwordValidator.isNormal(user.userPassword())) {
            result.add(LoadValidationError.of("userPassword", LoadValidationTypeError.INCORRECT));
        }

        if (user.userName() == null || user.userName().trim().isEmpty()) {
            result.add(LoadValidationError.of("userName", LoadValidationTypeError.EMPTY));
        } else if (!nameValidator.isNormal(user.userName())) {
            result.add(LoadValidationError.of("userName", LoadValidationTypeError.INCORRECT));
        }

        if (user.userSurname() == null || user.userSurname().trim().isEmpty()) {
            result.add(LoadValidationError.of("userSurname", LoadValidationTypeError.EMPTY));
        } else if (!nameValidator.isNormal(user.userSurname())) {
            result.add(LoadValidationError.of("userSurname", LoadValidationTypeError.INCORRECT));
        }

        if (user.birthdayDate() == null || user.birthdayDate().trim().isEmpty()) {
            result.add(LoadValidationError.of("birthdayDate", LoadValidationTypeError.EMPTY));
        } else if (!dateValidator.isNormal(user.birthdayDate())) {
            result.add(LoadValidationError.of("birthdayDate", LoadValidationTypeError.INCORRECT));
        }
        return result;
    }
}
