package managers;

import repository.UserExistRepository;
import repository.UsersRepository;
import treats.updates.UserUpdateCreator;

public class RepositoryManager {
    public UsersRepository userRepository = new UsersRepository(new UserUpdateCreator());
    public UserExistRepository userExistRepository = new UserExistRepository();
}
