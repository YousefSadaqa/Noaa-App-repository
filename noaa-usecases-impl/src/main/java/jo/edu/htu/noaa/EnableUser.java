package jo.edu.htu.noaa;

import jo.edu.htu.UsersRepository;
import jo.edu.htu.noaa.users.EnableUserHandler;

public class EnableUser implements EnableUserHandler {
    private UsersRepository repository;

    public EnableUser(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public void enable(String username) {
        repository.enable(username);
    }
}
