package treats.validators;

import java.util.regex.Pattern;

public class PasswordValidator {
    private final Pattern PASS = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)\\S[a-zA-Z\\d]{5,20}");

    public boolean isNormal(String pass){
        return PASS.matcher(pass).matches();
    }
}
