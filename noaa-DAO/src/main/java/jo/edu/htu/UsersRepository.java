package jo.edu.htu;

import jo.edu.htu.noaa.User;

import java.util.List;

public interface UsersRepository {
    void addUser(User user);

    void changePassword(String username, String oldPassword, String newPassword);

    int authenticate(String username, String password);

    void disable(String username);

    void enable(String username);

    List<User> listAll();

}
