package treats.validators.load;

import lombok.Value;

@Value(staticConstructor = "of")
public class LoadValidationError {
    String field;
    LoadValidationTypeError typeError;
}
