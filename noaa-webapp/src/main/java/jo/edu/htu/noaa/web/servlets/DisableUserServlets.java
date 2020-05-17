package jo.edu.htu.noaa.web.servlets;

import com.mysql.cj.Session;
import jo.edu.htu.DBUsersRepository;
import jo.edu.htu.noaa.User;
import jo.edu.htu.noaa.users.DisableUserHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class DisableUserServlets extends HttpServlet {

    DisableUserHandler disableUserHandler;

    public DisableUserServlets(DisableUserHandler disableUserHandler) {

        this.disableUserHandler = disableUserHandler;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forwardToView(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = null;
        String username = String.valueOf(session.getAttribute("username"));
        disableUserHandler.disable(username);
        forwardToView(req, resp);
    }

    private void forwardToView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/change-password.jsp");
        requestDispatcher.forward(req, resp);
    }
}
