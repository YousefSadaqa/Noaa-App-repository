package jo.edu.htu.noaa;

import jo.edu.htu.DBStationRepository;
import jo.edu.htu.DaoException;
import jo.edu.htu.StationRepository;
import jo.edu.htu.noaa.stations.ImportStationsHandler;
import jo.edu.htu.noaa.stations.ImportStationsRequest;
import jo.edu.htu.noaa.stations.ImportStationsResult;
import org.apache.commons.dbcp2.BasicDataSource;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

public class importTest {
    public static void main(String[] args) {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("root");
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/countries?serverTimezone=UTC");
        basicDataSource.setInitialSize(2);
        try {
            try (Connection connection = basicDataSource.getConnection()) {


//        MysqlDataSource dataSource = new MysqlDataSource();
//        dataSource.setUser("root");
//        dataSource.setPassword("root");
//        dataSource.setURL("jdbc:mysql://localhost:3306/countries?serverTimezone=UTC");
                StationRepository repository = new DBStationRepository(basicDataSource);
                ImportStationsHandler stationsHandler = new ImportStation(repository);

                long startTime = System.currentTimeMillis();
                System.out.println("start processing: " + new Date());
                ImportStationsRequest request = new ImportStationsRequest(Paths.get(".", "Stations.txt"));
                ImportStationsResult result = stationsHandler.importStations(request);
                long endTime = System.currentTimeMillis();
                System.out.println("New :" + result.getNewRecords() + " TotalPars :" + result.getTotal() + " Total in DB :" + result.getTotalInDatabase() + " Updated :" + result.getUpdated());
                System.out.println("import took: " + ((endTime - startTime) / 1000) + " seconds");
                System.out.println("end: " + new Date());

            } catch (SQLException throwables) {
                throw new DaoException();
            }

        } catch (DaoException daoException) {
            daoException.printStackTrace();
        }
    }
}

