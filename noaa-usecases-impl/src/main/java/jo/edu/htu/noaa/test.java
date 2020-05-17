package jo.edu.htu.noaa;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import jo.edu.htu.*;
import jo.edu.htu.noaa.gsod.ImportGSODRequest;

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
        // testAuthenticate(dataSource);
        //insertTest(dataSource);
        //testFind(dataSource);
        //testUpdate(dataSource);
        //testfind1(dataSource);
        //listAllGsod(dataSource);
        // testFindStationByPrimaryKey(dataSource);
        //addUserTest(dataSource);
        //changePassword(dataSource);
        //changeStatusTest(dataSource);
        //listAllUsers(dataSource);
        listAllGsodByDate(dataSource);
    }

    private static void testAuthenticate(MysqlConnectionPoolDataSource dataSource) {
        DBUsersRepository repository = new DBUsersRepository(dataSource);
        int authenticate = repository.authenticate("aliiii", "12312");
        System.out.println(authenticate);
    }

    private static void listAllUsers(MysqlConnectionPoolDataSource dataSource) {

        DBUsersRepository repository = new DBUsersRepository(dataSource);
        List<User> Users = repository.listAll();
        for (User user : Users) {
            System.out.println(user.getName());

        }

    }

    private static void changeStatusTest(MysqlConnectionPoolDataSource dataSource) {
        DBUsersRepository repository = new DBUsersRepository(dataSource);
        repository.disable("ysadqa");
    }

    private static void changePassword(DataSource dataSource) {
        DBUsersRepository repository = new DBUsersRepository(dataSource);
        repository.changePassword("ysadqa", "123321", "ysq111");
    }

    private static void addUserTest(DataSource dataSource) {
        DBUsersRepository repository = new DBUsersRepository(dataSource);
        User user = new User();
        user.setEmail("Ali_12312@yahoo.com");
        user.setPassword("112312");
        user.setName("Ali");
        user.setStatus(Status.INACTIVE);
        user.setUsername("aliiii");
        repository.addUser(user);
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

    private static void listAllGsodByDate(DataSource dataSource) {
        DBGlobalSummaryOfDayRepository dbStationRepository = new DBGlobalSummaryOfDayRepository(dataSource);
        List<GlobalSummaryOfDay> GsodList = dbStationRepository.listAllStationsFilterByDate(2017902, 2017905);
        for (GlobalSummaryOfDay globalSummaryOfDay : GsodList) {
            System.out.println(globalSummaryOfDay.getStn());
        }
        System.out.println();
    }

    private static void testFindStationByPrimaryKey(DataSource dataSource) {
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


