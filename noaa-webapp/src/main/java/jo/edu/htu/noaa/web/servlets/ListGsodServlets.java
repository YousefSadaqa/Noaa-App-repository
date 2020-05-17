package jo.edu.htu.noaa.web.servlets;

import jo.edu.htu.noaa.GlobalSummaryOfDay;
import jo.edu.htu.noaa.gsod.ListGSODHandler;
import jo.edu.htu.noaa.gsod.ListGSODRequest;
import jo.edu.htu.noaa.gsod.ListGSODResult;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ListGsodServlets extends HttpServlet {
    private ListGSODHandler listGSODHandler;

    public ListGsodServlets(ListGSODHandler listGSODHandler) {
        this.listGSODHandler = listGSODHandler;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        refreshView(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        refreshView(req, resp);
    }

    private void refreshView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String usaf = req.getParameter("usaf");
        String wban = req.getParameter("wban");
        String begin = req.getParameter("begin");
        String end = req.getParameter("end");
        ListGSODResult gsodResult = listGSODHandler.list(new ListGSODRequest(usaf, wban, begin, end));
        List<GlobalSummaryOfDay> gsods = gsodResult.getGsods();
        req.setAttribute("gsod", gsods);
        forwardToView(req, resp);

    }

    private void forwardToView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/list-gsod.jsp");
        requestDispatcher.forward(req, resp);
    }
}
