import jo.edu.htu.noaa.Station;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DBStationRepository implements StationRepository {
    private static final String UPDATE_STATION = "update station set Station_Name=?,CTRY=?," +
            "ST=?,ICAO=?,LAT=?,LON=?,ELEV=?,Begin_Date=?,End_Date=? where USAF like ?AND WBAN=?";
    private static final String INSERT_STATION_SQL = "insert into station " +
            "(USAF,WBAN,Station_Name,CTRY,ST,ICAO,LAT,LON,ELEV,Begin_Date ,End_Date) " +
            "values(?,?,?,?,?,?,?,?,?,?,?)";
    private static final String QUERY_STATIONS_BY_LOCATION = "select USAF,WBAN, Station_Name,CTRY,LAT,LON " +
            " from station where LAT=? AND LON=? ORDER BY  LAT , LON ";
    private static final String QUERY_STATIONS_BY_PRIMARY_KEY = "select USAF,WBAN, Station_Name,CTRY,LAT,LON" +
            " from station where USAF like ? AND WBAN=?  ORDER BY USAF ;";
    private static final String QUERY_COUNT_STATION = "Select count(USAF)from station;";
    private static final String QUERY_ALL_STATIONS = "select USAF,WBAN, Station_Name,CTRY,LAT,LON" +
            " from station ;";


    private DataSource dataSource;

    public DBStationRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int count() {
        int count = 0;
        try {
            try (Connection connection = dataSource.getConnection()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_COUNT_STATION)) {
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        resultSet.next();
                        count = resultSet.getInt(1);
                    }
                }
            }
            return count;
        } catch (Throwable daoException) {
            throw new DaoException();
        }

    }

    @Override
    public void insert(Station station) {
        insertUsingBatch(station);
    }

    @Override
    public List<Station> listAllStations() {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_ALL_STATIONS)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    List<Station> stationList = new ArrayList<>();
                    while (resultSet.next()) {
                        Station station = new Station();
                        station.setUsaf(resultSet.getString("USAF"));
                        station.setWban(resultSet.getString("WBAN"));
                        station.setName(resultSet.getString("Station_Name"));
                        station.setCtry(resultSet.getString("CTRY"));
                        station.setLatitude(resultSet.getBigDecimal("LAT"));
                        station.setLongitude(resultSet.getBigDecimal("LON"));
                        stationList.add(station);
                    }
                    return stationList;
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public List<Station> listAllStationsFilterByLocation(BigDecimal lat, BigDecimal lon) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(QUERY_ALL_STATIONS)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    List<Station> stations = new LinkedList<>();
                    while (resultSet.next()) {
                        Station station = new Station();
                        station.setUsaf(resultSet.getString("USAF"));
                        station.setWban(resultSet.getString("WBAN"));
                        station.setName(resultSet.getString("Station_Name"));
                        station.setCtry(resultSet.getString("CTRY"));
                        station.setLatitude(resultSet.getBigDecimal("LAT"));
                        station.setLongitude(resultSet.getBigDecimal("LON"));
                        stations.add(station);
                    }
                    return stations;
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public List<Station> StationsFilterByPrimary(String usaf, int wban) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_STATIONS_BY_PRIMARY_KEY)) {
                preparedStatement.setString(1, "%" + usaf + "%");
                preparedStatement.setInt(2, wban);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    List<Station> stations = new ArrayList<>();
                    while (resultSet.next()) {
                        Station station = new Station();
                        station.setUsaf(resultSet.getString("USAF"));
                        station.setWban(resultSet.getString("WBAN"));
                        station.setName(resultSet.getString("Station_Name"));
                        station.setCtry(resultSet.getString("CTRY"));
                        station.setLatitude(resultSet.getBigDecimal("LAT"));
                        station.setLongitude(resultSet.getBigDecimal("LON"));
                        stations.add(station);
                    }
                    return stations;
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void update(Station station) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_STATION)) {
                statement.setString(1, station.getName());
                statement.setString(2, station.getCtry());
                statement.setString(3, station.getState());
                statement.setString(4, station.getIcao());
                statement.setBigDecimal(5, station.getLatitude());
                statement.setBigDecimal(6, station.getLongitude());
                statement.setBigDecimal(7, station.getELEV());
                statement.setString(8, station.getBegin());
                statement.setString(9, station.getEnd());
                statement.setString(10, "%" + station.getUsaf() + "%");
                statement.setString(11, station.getWban());
                int updated = statement.executeUpdate();
                if (updated == 0) {
                    throw new RecordNotFoundException("No rows Were updated: " + station.getUsaf() + "-" + station.getWban());
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }


    private void insertUsingBatch(Station station) {
        try {
            try (Connection connection = dataSource.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(INSERT_STATION_SQL)) {
                    fillPrepareStatement(statement, station);
                    statement.executeBatch();
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private void fillPrepareStatement(PreparedStatement statement, Station st) throws SQLException {
        statement.setString(1, st.getUsaf());
        statement.setString(2, st.getWban());
        statement.setString(3, st.getName());
        statement.setString(4, st.getCtry());
        statement.setString(5, st.getState());
        statement.setString(6, st.getIcao());
        statement.setBigDecimal(7, (st.getLatitude()));
        statement.setBigDecimal(8, st.getLongitude());
        statement.setBigDecimal(9, st.getELEV());
        statement.setString(10, st.getBegin());
        statement.setString(11, st.getEnd());
        statement.addBatch();
    }

}
