package jo.edu.htu.noaa.web.servlets;


import jo.edu.htu.noaa.users.AuthenticationException;
import jo.edu.htu.noaa.users.AuthenticationHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthenticationServlets extends HttpServlet {
    private AuthenticationHandler authenticationHandler;

    public AuthenticationServlets(AuthenticationHandler authenticationHandler) {
        this.authenticationHandler = authenticationHandler;

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forwardToView(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        // are those valid credentials
        try {
            authenticationHandler.authenticate(username, password);
            HttpSession session = req.getSession(true);
            session.setAttribute("currentUser", username);
            req.getRequestDispatcher("/home.html").forward(req, resp);
        } catch (AuthenticationException e) {
            req.setAttribute("invalidLogin", true);
            forwardToView(req, resp);
        }
    }


    private void forwardToView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }
}
