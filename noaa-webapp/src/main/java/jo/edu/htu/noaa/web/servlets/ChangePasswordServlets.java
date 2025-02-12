package jo.edu.htu.noaa.web.servlets;

import com.mysql.cj.Session;
import jo.edu.htu.noaa.users.ChangePasswordHandler;
import jo.edu.htu.noaa.users.ChangePasswordRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ChangePasswordServlets extends HttpServlet {
    ChangePasswordHandler changePasswordHandler;

    public ChangePasswordServlets(ChangePasswordHandler changePasswordHandler) {
        this.changePasswordHandler = changePasswordHandler;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        HttpSession session = req.getSession(true);
        String username = String.valueOf(session.getAttribute("username"));
        changePasswordHandler.changePassword(new ChangePasswordRequest(username, oldPassword, newPassword));
        forwardToView(req, resp);
    }

    private void forwardToView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/change-password.jsp");
        requestDispatcher.forward(req, resp);
    }
}
