package jo.edu.htu.noaa.web.servlets;

import jo.edu.htu.DBUsersRepository;
import jo.edu.htu.noaa.EnableUser;
import jo.edu.htu.noaa.users.DisableUserHandler;
import jo.edu.htu.noaa.users.EnableUserHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EnableUsersServlets extends HttpServlet {
    public EnableUsersServlets(EnableUserHandler enableUserHandler) {
        this.enableUserHandler = enableUserHandler;
    }

    EnableUserHandler enableUserHandler;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = null;
        String username = String.valueOf(session.getAttribute("username"));
        enableUserHandler.enable(username);
        forwardToView(req, resp);
    }

    private void forwardToView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/change-password.jsp");
        requestDispatcher.forward(req, resp);
    }
}
