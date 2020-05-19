package jo.edu.htu.noaa;

import jo.edu.htu.UsersRepository;
import jo.edu.htu.noaa.users.AddUserHandler;

public class AddUser implements AddUserHandler {

    private UsersRepository repository;

    public AddUser(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addUser(User user) {
        repository.addUser(user);
    }
}
