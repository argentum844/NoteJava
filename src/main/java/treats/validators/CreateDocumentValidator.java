package treats.validators;

import dto.Documents.View.DocumentInsertDTO;
import dto.Users.Controller.UsersControllerDTO;
import treats.validators.load.LoadValidationError;
import treats.validators.load.LoadValidationResult;
import treats.validators.load.LoadValidationTypeError;

import java.util.regex.Pattern;

public class CreateDocumentValidator {
    public LoadValidationResult isValid(DocumentInsertDTO dto){
        LoadValidationResult result = new LoadValidationResult();
        if (dto.title() == null || dto.title().isEmpty()){
            result.add(LoadValidationError.of("title", LoadValidationTypeError.EMPTY));
        } else if (!titleValidator(dto.title())){
            result.add(LoadValidationError.of("title", LoadValidationTypeError.INCORRECT));
        }
        return result;
    }

    private boolean titleValidator(String title){
        return Pattern.compile("^\\w{5,50}$").matcher(title).matches();
    }
}
