package jo.edu.htu.noaa.web.servlets;

import jo.edu.htu.DBUsersRepository;
import jo.edu.htu.UsersRepository;
import jo.edu.htu.noaa.ListAllUsers;
import jo.edu.htu.noaa.User;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserManagementServlets extends HttpServlet {
    private ListAllUsers listAllUsers;

    public UserManagementServlets(ListAllUsers listAllUsers) {
        this.listAllUsers = listAllUsers;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = listAllUsers.list();
        req.setAttribute("users", users);
        forwardToView(req, resp);
    }

    private void forwardToView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/user-management.jsp");
        requestDispatcher.forward(req, resp);
    }
}
