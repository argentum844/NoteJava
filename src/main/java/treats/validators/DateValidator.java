package treats.validators;

import mapper.converter.ConvertString;

import java.time.format.DateTimeParseException;
import java.util.Optional;

public class DateValidator {
    public boolean isNormal(String date){
        try{
            return Optional.of(date)
                    .map(ConvertString::toDate)
                    .isPresent();
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
