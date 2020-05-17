package jo.edu.htu.noaa;

import jo.edu.htu.DBUsersRepository;
import jo.edu.htu.noaa.User;
import jo.edu.htu.noaa.users.AddUserHandler;

public class AddUser implements AddUserHandler {

    private DBUsersRepository repository;

    public AddUser(DBUsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addUser(User user) {
        repository.addUser(user);
    }
}
