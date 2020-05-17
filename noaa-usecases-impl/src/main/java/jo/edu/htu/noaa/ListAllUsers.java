package jo.edu.htu.noaa;

import jo.edu.htu.DBUsersRepository;

import java.util.List;

public class ListAllUsers {
    private DBUsersRepository dbUsersRepository;

    public ListAllUsers(DBUsersRepository dbUsersRepository) {
        this.dbUsersRepository = dbUsersRepository;
    }

    public List<User> list() {
        List<User> users = dbUsersRepository.listAll();
        return users;
    }
}
