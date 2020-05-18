package jo.edu.htu.noaa.web.servlets;

import jo.edu.htu.noaa.stations.ImportStationsHandler;
import jo.edu.htu.noaa.stations.ImportStationsRequest;
import jo.edu.htu.noaa.stations.ImportStationsResult;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
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

        addCookies(resp, importStationsResult);
        forwardToView(req, resp);
    }

    private void addCookies(HttpServletResponse resp, ImportStationsResult importStationsResult) {
        String totalParsed = String.valueOf(importStationsResult.getTotal());
        String newRecords = String.valueOf(importStationsResult.getNewRecords());
        String updatedRecords = String.valueOf(importStationsResult.getUpdated());
        String totalInDatabase = String.valueOf(importStationsResult.getTotalInDatabase());
        Cookie updatedRecordsCookie = new Cookie("updatedRecords", updatedRecords);
        Cookie totalInDatabaseCookie = new Cookie("totalInDatabase", totalInDatabase);
        Cookie newRecordsCookie = new Cookie("newRecord", newRecords);
        Cookie totalParsedCookie = new Cookie("totalParsed", totalParsed);
        resp.addCookie(newRecordsCookie);
        resp.addCookie(totalParsedCookie);
        resp.addCookie(updatedRecordsCookie);
        resp.addCookie(totalInDatabaseCookie);
    }

    private void forwardToView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/import-stations.jsp");
        requestDispatcher.forward(req, resp);

    }
}
