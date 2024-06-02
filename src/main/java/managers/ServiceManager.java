package managers;

import service.UserService;
import treats.hashed.PasswordHashed;

public class ServiceManager {
    public final UserService userService = new UserService(new PasswordHashed(),
            new RepositoryManager().userRepository, new ValidationManager().updateUserValidator,
            new ValidationManager().insertUserValidator);

}
