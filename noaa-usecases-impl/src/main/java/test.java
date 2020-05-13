import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import jo.edu.htu.noaa.GlobalSummaryOfDay;
import jo.edu.htu.noaa.Station;
import jo.edu.htu.noaa.gsod.ImportGSODRequest;
import jo.edu.htu.noaa.stations.ImportStationsHandler;
import jo.edu.htu.noaa.stations.ImportStationsRequest;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class test {
    public static void main(String[] args) {


        MysqlDataSource factory;
        MysqlConnectionPoolDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/countries?serverTimezone=UTC");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        //insertTest(dataSource);
        //testFind(dataSource);
        //testUpdate(dataSource);
        //testfind1(dataSource);
        listAll(dataSource);

    }

    private static void testUpdate(MysqlConnectionPoolDataSource dataSource) {
        try {
            try (Connection connection = dataSource.getConnection()) {

                StationRepository repository = new DBStationRepository(dataSource);
                Station station = new Station();
                station.setUsaf("007011");
                station.setWban("99999");
                station.setName("Amm");
                station.setCtry("Jordan");
                station.setIcao("kk");
                station.setELEV(new BigDecimal(12.33).setScale(3, RoundingMode.DOWN));
                station.setLongitude(new BigDecimal(12.33).setScale(3, RoundingMode.DOWN));
                station.setLatitude(new BigDecimal(12.33).setScale(3, RoundingMode.DOWN));
                station.setState("ST");
                station.setBegin("2015");
                station.setEnd("2020");
                repository.update(station);
            } catch (SQLException throwables) {

                throw new RecordNotFoundException("Not Found");
            }

        } catch (RecordNotFoundException e) {
            throw new RecordNotFoundException("Not Found");
        }
    }

    private static void testfind1(MysqlConnectionPoolDataSource dataSource) {
        DBStationRepository stationRepository = new DBStationRepository(dataSource);
        List<Station> stations = stationRepository.listAllStationsFilterByLocation(BigDecimal.valueOf(70.483), BigDecimal.valueOf(22.150));
        for (Station station : stations) {
            System.out.println(station.getUsaf());
            System.out.println(station.getWban());
            System.out.println(station.getCtry());
            System.out.println(station.getName());
            System.out.println(station.getLatitude());
        }

    }

    private static void listAll(DataSource dataSource) {
        DBGlobalSummaryOfDayRepository dbStationRepository = new DBGlobalSummaryOfDayRepository(dataSource);
        List<GlobalSummaryOfDay> gsodlist = dbStationRepository.listAllStations();
        for (GlobalSummaryOfDay globalSummaryOfDay : gsodlist) {
            System.out.println(globalSummaryOfDay.getStn());

        }

    }

    private static void testFind(DataSource dataSource) {
        DBStationRepository statsionRepository = new DBStationRepository(dataSource);
        List<Station> stations = statsionRepository.StationsFilterByPrimary("88", 99999);
        for (Station station : stations) {
            System.out.println(station.getName());

        }
    }

    private static void insertTest(MysqlConnectionPoolDataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {

            GsodRepository repository = new DBGlobalSummaryOfDayRepository(dataSource);
            ImportGlobalSummaryOfDay globalSummaryOfDay = new ImportGlobalSummaryOfDay(repository);
            long startTime = System.currentTimeMillis();
            System.out.println("start processing: " + new Date());
            ImportGSODRequest request = new ImportGSODRequest(Paths.get(".", "CDO9595867443733-us-20170902.txt"));
            globalSummaryOfDay.importGSOD(request);
            long endTime = System.currentTimeMillis();
            System.out.println("import took: " + ((endTime - startTime) / 1000) + " seconds");
            System.out.println("end: " + new Date());
        }

    }
}


