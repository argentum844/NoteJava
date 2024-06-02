package treats.validators;

import java.util.regex.Pattern;

public class LoginValidator {
    private final Pattern LOGIN = Pattern.compile("^\\w{5,50}$");

    public boolean isNormal(String pass){
        return LOGIN.matcher(pass).matches();
    }
}
