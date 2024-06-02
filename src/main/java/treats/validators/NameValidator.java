package treats.validators;

import java.util.regex.Pattern;

public class NameValidator {
    private final Pattern NAME = Pattern.compile("^[a-zA-Z]{2,50}$");

    public boolean isNormal(String pass){
        return NAME.matcher(pass).matches();
    }
}
