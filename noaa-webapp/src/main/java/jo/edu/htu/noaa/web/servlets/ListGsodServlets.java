package jo.edu.htu.noaa.web.servlets;

import jo.edu.htu.noaa.gsod.ListGSODHandler;
import jo.edu.htu.noaa.gsod.ListGSODRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListGsodServlets extends HttpServlet {
    private ListGSODHandler listGSODHandler;

    public ListGsodServlets(ListGSODHandler listGSODHandler) {
        this.listGSODHandler = listGSODHandler;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String usaf = String.valueOf(req.getAttribute("usaf"));
        String wban = String.valueOf(req.getAttribute("wban"));
        String begin = String.valueOf(req.getAttribute("begin"));
        String end = String.valueOf(req.getAttribute("end"));
        listGSODHandler.list(new ListGSODRequest(usaf, wban, begin, end));
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/list-gsod.jsp");
        requestDispatcher.forward(req, resp);
    }
}
