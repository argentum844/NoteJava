package treats.validators.load;

import java.util.ArrayList;
import java.util.List;

public class LoadValidationResult {
    private final List<LoadValidationError> loadValidationErrors = new ArrayList<>();

    public List<LoadValidationError> getLoadValidationErrors() {
        return loadValidationErrors;
    }
    public void add(LoadValidationError err){
        loadValidationErrors.add(err);
    }
    public boolean isEmpty(){
        return loadValidationErrors.isEmpty();
    }

}
