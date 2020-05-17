package jo.edu.htu.noaa.web.servlets;

import jo.edu.htu.UsersRepository;
import jo.edu.htu.noaa.Status;
import jo.edu.htu.noaa.User;
import jo.edu.htu.noaa.users.AddUserHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AddUserServlets extends HttpServlet {
    AddUserHandler addUserHandler;


    public AddUserServlets(AddUserHandler addUserHandler) {
        this.addUserHandler = addUserHandler;

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forwardToView(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = String.valueOf(req.getAttribute("username"));
        String name = String.valueOf(req.getAttribute("name"));
        String email = String.valueOf(req.getAttribute("email"));
        String password = String.valueOf(req.getAttribute("password"));
        String status = String.valueOf(req.getAttribute("status"));
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setStatus(Status.valueOf(status));
        user.setPassword(password);
        addUserHandler.addUser(user);
    }

    private void forwardToView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/add-user.jsp").forward(req, resp);
    }
}
