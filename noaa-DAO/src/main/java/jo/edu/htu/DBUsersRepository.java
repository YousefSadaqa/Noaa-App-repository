package jo.edu.htu;

import jo.edu.htu.noaa.Status;
import jo.edu.htu.noaa.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUsersRepository implements UsersRepository {
    private static final String ADD_USER_QUERY = "insert into users " +
            "(Username,Password,Name,Email,status) " +
            "values(?,?,?,?,?)";
    private static final String CHANGE_PASSWORD = "Update users set Password=? where Password=? And Username=?";
    private static final String CHANGE_STATUS = "Update users set status =? where Username=?";
    private static final String LIST_ALL = "Select Username,name,Email,status from users ";
    private static final String AUTHENTICATE_QUERY = "Select count(Username) from users where Username=? AND Password=? ";
    private DataSource dataSource;

    public DBUsersRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addUser(User user) {
        try {
            try (Connection connection = dataSource.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(ADD_USER_QUERY)) {
                    statement.setString(1, user.getUsername());
                    statement.setString(2, user.getPassword());
                    statement.setString(3, user.getName());
                    statement.setString(4, user.getEmail());
                    statement.setString(5, String.valueOf(user.getStatus()));
                    statement.addBatch();
                    statement.executeBatch();
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public int authenticate(String username, String password) {
        int authenticated = 0;
        try {
            try (Connection connection = dataSource.getConnection()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(AUTHENTICATE_QUERY)) {
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        resultSet.next();
                        authenticated = resultSet.getInt(1);
                    }
                }
            }
            return authenticated;
        } catch (Throwable daoException) {
            throw new DaoException();
        }

    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(CHANGE_PASSWORD)) {
                statement.setString(1, newPassword);
                statement.setString(2, oldPassword);
                statement.setString(3, username);
                int updated = statement.executeUpdate();
                if (updated == 0) {
                    throw new RecordNotFoundException("No rows Were updated");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void disable(String username) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(CHANGE_STATUS)) {
                statement.setString(1, String.valueOf(Status.INACTIVE));
                statement.setString(2, username);
                int updated = statement.executeUpdate();
                if (updated == 0) {
                    throw new RecordNotFoundException("No rows Were updated");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void enable(String username) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(CHANGE_STATUS)) {
                statement.setString(1, String.valueOf(Status.ACTIVE));
                statement.setString(2, username);
                int updated = statement.executeUpdate();
                if (updated == 0) {
                    throw new RecordNotFoundException("No rows Were updated");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> listAll() {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(LIST_ALL)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    List<User> users = new ArrayList<>();
                    while (resultSet.next()) {
                        User user = new User();
                        user.setName(resultSet.getString("Name"));
                        user.setUsername(resultSet.getString("Username"));
                        user.setEmail(resultSet.getString("Email"));
                        user.setStatus(Status.valueOf(resultSet.getString("status")));
                        users.add(user);
                    }
                    return users;
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
