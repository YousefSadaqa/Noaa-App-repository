package jo.edu.htu.noaa;

import jo.edu.htu.UsersRepository;
import jo.edu.htu.noaa.users.DisableUserHandler;

public class DisableUser implements DisableUserHandler {
    private UsersRepository repository;

    public DisableUser(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public void disable(String username) {
        repository.disable(username);
    }
}
