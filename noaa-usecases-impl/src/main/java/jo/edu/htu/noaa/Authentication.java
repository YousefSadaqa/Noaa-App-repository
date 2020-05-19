package jo.edu.htu.noaa;

import jo.edu.htu.DBUsersRepository;
import jo.edu.htu.noaa.users.AuthenticationException;
import jo.edu.htu.noaa.users.AuthenticationHandler;

public class Authentication implements AuthenticationHandler {
    private DBUsersRepository repository;

    public Authentication(DBUsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public void authenticate(String username, String password) throws AuthenticationException {
        int authenticated = repository.authenticate(username, password);

    }
}
