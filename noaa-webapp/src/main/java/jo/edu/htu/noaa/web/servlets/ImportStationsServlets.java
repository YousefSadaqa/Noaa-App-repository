package jo.edu.htu.noaa.web.servlets;

import jo.edu.htu.noaa.stations.ImportStationsHandler;
import jo.edu.htu.noaa.stations.ImportStationsRequest;
import jo.edu.htu.noaa.stations.ImportStationsResult;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImportStationsServlets extends HttpServlet {
    private final ImportStationsHandler importStationsHandler;
    private Path tempDirectory;

    public ImportStationsServlets(ImportStationsHandler importStationsHandler) {
        this.importStationsHandler = importStationsHandler;
    }

    @Override
    public void init() throws ServletException {
        String userHome = System.getProperty("user.home");
        tempDirectory = Paths.get(userHome).resolve("stations-temp");
        try {
            Files.createDirectories(tempDirectory);
        } catch (IOException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileDate = req.getParameter("fileDate");
        System.out.println("fileDate parameter: " + fileDate);
        String bisFile = req.getParameter("stationsFile");
        System.out.println("stationsFile as parameter: " + bisFile);

        Part part = req.getPart("stationsFile");
        System.out.println("name: " + part.getName());
        System.out.println("content-type: " + part.getContentType());
        System.out.println("size of the file: " + part.getSize());

        Path fileToImport = Files.createTempFile(part.getSubmittedFileName(), ".temp");
        System.out.println(fileToImport);
        part.write(fileToImport.toString());

        ImportStationsResult importStationsResult = importStationsHandler.importStations(new ImportStationsRequest(fileToImport));

        req.setAttribute("total number of parsed records", importStationsResult.getTotal());
        req.setAttribute("new records", importStationsResult.getNewRecords());
        req.setAttribute("Updated records", importStationsResult.getUpdated());
        req.setAttribute("total Stations in Database ", importStationsResult.getTotalInDatabase());

        forwardToView(req, resp);
    }

    private void forwardToView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/import-stations.jsp");
        requestDispatcher.forward(req, resp);

    }
}
