package jo.edu.htu.noaa.web.servlets;

import jo.edu.htu.noaa.gsod.ImportGSODHandler;
import jo.edu.htu.noaa.gsod.ImportGSODRequest;
import jo.edu.htu.noaa.gsod.ImportGSODResult;
import jo.edu.htu.noaa.stations.ImportStationsHandler;
import jo.edu.htu.noaa.stations.ImportStationsRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImportGsodServlets extends HttpServlet {
    private final ImportGSODHandler importGSODHandler;
    private Path tempDirectory;

    public ImportGsodServlets(ImportGSODHandler importGSODHandler) {
        this.importGSODHandler = importGSODHandler;
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
        forwardToView(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileDate = req.getParameter("fileDate");
        String gsodFile = req.getParameter("stationsFile");

        Part part = req.getPart("gsodFile");
        Path fileToImport = Files.createTempFile(part.getSubmittedFileName(), ".temp");
        part.write(fileToImport.toString());

        ImportGSODResult importGSODResult = importGSODHandler.importGSOD(new ImportGSODRequest(fileToImport));
        addCookies(resp, importGSODResult);
        forwardToView(req, resp);
    }

    private void addCookies(HttpServletResponse resp, ImportGSODResult importGSODResult) {
        String totalParsed = String.valueOf(importGSODResult.getTotal());
        String newRecords = String.valueOf(importGSODResult.getNewRecords());
        String updatedRecords = String.valueOf(importGSODResult.getUpdated());
        String totalInDatabase = String.valueOf(importGSODResult.getTotalInDatabase());
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
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/import-gsod.jsp");
        requestDispatcher.forward(req, resp);
    }
}
