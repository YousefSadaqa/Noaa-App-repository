import jo.edu.htu.DBStationRepository;
import jo.edu.htu.StationRepository;
import jo.edu.htu.noaa.Station;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;

public class JdbcTest {
    public static void main(String[] args) {

        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/countries?serverTimezone=UTC");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("root");
        basicDataSource.setMaxTotal(10);
        basicDataSource.setMinIdle(3);
        Station station = new Station();
        testInsert(station, basicDataSource);
    }

    private static void testInsert(Station station, DataSource dataSource) {
        StationRepository repository = new DBStationRepository(dataSource);
        repository.insert(station);
        System.out.println("done");

    }

}

