package treats.validators.load;

public enum LoadValidationTypeError {
    EMPTY("Empty value"),
    NOT_UNIQUE("Not unique value!"),
    INCORRECT("Wrong value!");

    private final String type;

    LoadValidationTypeError(String type){
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }
}
