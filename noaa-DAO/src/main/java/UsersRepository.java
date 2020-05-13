import jo.edu.htu.noaa.User;

public interface UsersRepository {
    void addUser(User user);

    void authenticate();

    void changePassword(String username, String oldPassword, String newPassword);

    void disable(String username);

    void enable(String username);

}
