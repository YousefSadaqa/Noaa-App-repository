package jo.edu.htu.noaa;

import jo.edu.htu.UsersRepository;
import jo.edu.htu.noaa.users.ChangePasswordHandler;
import jo.edu.htu.noaa.users.ChangePasswordRequest;

public class ChangePassword implements ChangePasswordHandler {
    private UsersRepository repository;

    public ChangePassword(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        repository.changePassword(request.getUsername(), request.getOldPassword(), request.getNewPassword());

    }
}
