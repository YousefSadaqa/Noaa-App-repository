package jo.edu.htu.noaa.web.servlets;

import jo.edu.htu.noaa.stations.ListStationsHandler;
import jo.edu.htu.noaa.stations.ListStationsRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ListStationsServlets extends HttpServlet {
    private final ListStationsHandler listStationsHandler;

    public ListStationsServlets(ListStationsHandler listStationsHandler) {
        this.listStationsHandler = listStationsHandler;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        refreshView(req, resp);
        return;

    }

    private void refreshView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String usaf = req.getParameter("usaf");
        String wban =req.getParameter("wban");
        Double lan = Double.valueOf(req.getParameter("lon"));
        Double lat = Double.valueOf(req.getParameter("lot"));
        listStationsHandler.list(new ListStationsRequest(usaf, wban, lan, lat));
        forwardToView(req, resp);
        return;
    }

    private void forwardToView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/list-stations.jsp");
        requestDispatcher.forward(req, resp);
    }
}
