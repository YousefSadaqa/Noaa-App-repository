package jo.edu.htu.noaa.web.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ImportResultServlets extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        Cookie newRecordsCookie = cookies[0];
        Cookie totalParsedCookie = cookies[1];
        Cookie updatedRecordsCookie = cookies[2];
        Cookie totalInDatabaseCookie = cookies[3];
        forwardToView(req, resp);

    }


    private void forwardToView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/import-result.jsp");
        requestDispatcher.forward(req, resp);

    }
}
