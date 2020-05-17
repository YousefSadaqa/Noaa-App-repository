package jo.edu.htu.noaa.web.servlets;

import jo.edu.htu.noaa.gsod.ImportGSODHandler;
import jo.edu.htu.noaa.gsod.ImportGSODRequest;
import jo.edu.htu.noaa.gsod.ImportGSODResult;
import jo.edu.htu.noaa.stations.ImportStationsHandler;
import jo.edu.htu.noaa.stations.ImportStationsRequest;

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
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileDate = req.getParameter("fileDate");
        System.out.println("fileDate parameter: " + fileDate);
        String gsodFile = req.getParameter("stationsFile");
        System.out.println("gsodFile as parameter: " + gsodFile);

        Part part = req.getPart("gsodFile");
        System.out.println("name: " + part.getName());
        System.out.println("content-type: " + part.getContentType());
        System.out.println("size of the file: " + part.getSize());
        Path fileToImport = Files.createTempFile(part.getSubmittedFileName(), ".temp");
        System.out.println(fileToImport);
        part.write(fileToImport.toString());

        ImportGSODResult importGSODResult = importGSODHandler.importGSOD(new ImportGSODRequest(fileToImport));
        req.setAttribute("total number of parsed records", importGSODResult.getTotal());
        req.setAttribute("new records", importGSODResult.getNewRecords());
        req.setAttribute("Updated records", importGSODResult.getUpdated());
        req.setAttribute("total Stations in Database ", importGSODResult.getTotalInDatabase());

        forwardToView(req, resp);
    }

    private void forwardToView(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/import-gsod.jsp");
        requestDispatcher.forward(req, resp);
    }
}
