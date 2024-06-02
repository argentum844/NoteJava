package treats.validators.logopass;

import treats.updates.UpdateStatusUser;

public enum UpdatePasswordError {
    BAD_NEW_PASSWORD("New password is incorrect!"),
    ERROR_PASSWORD("Inputed password not equals your password!");

    private final String type;

    UpdatePasswordError(String type){
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
