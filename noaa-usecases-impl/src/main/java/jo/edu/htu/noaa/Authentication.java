package jo.edu.htu.noaa;

import jo.edu.htu.UsersRepository;
import jo.edu.htu.noaa.users.AuthenticationException;
import jo.edu.htu.noaa.users.AuthenticationHandler;

public class Authentication implements AuthenticationHandler {
    private UsersRepository repository;

    public Authentication(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public void authenticate(String username, String password) throws AuthenticationException {
        int authenticated = repository.authenticate(username, password);

    }
}
