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
        String usaf = String.valueOf(req.getAttribute("usaf"));
        String wban = String.valueOf(req.getAttribute("wban"));
        Double lan = Double.parseDouble(String.valueOf(req.getAttribute("lan")));
        Double lat = Double.parseDouble(String.valueOf(req.getAttribute("lat")));
        listStationsHandler.list(new ListStationsRequest(usaf, wban, lan, lat));
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/list-stations.jsp");
        requestDispatcher.forward(req, resp);

    }
}
