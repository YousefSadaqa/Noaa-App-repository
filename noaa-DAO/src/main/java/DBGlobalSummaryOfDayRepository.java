import jo.edu.htu.noaa.GlobalSummaryOfDay;
import jo.edu.htu.noaa.Station;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DBGlobalSummaryOfDayRepository implements GsodRepository {
    private static final String INSERT_GSOD_SQL = "insert into gsod " +
            "(ST_NUMBER,WBAN,YEAR_MODA,TEMP,TEMP_COUNT,WDSP,WDSP_COUNT,MAX_TEMP,MAX_FLAG,MIN_TEMP ,MIN_FLAG) " +
            "values(?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_GSOD = "update gsod set YEAR_MODA=?,TEMP=?,TEMP_COUNT=?,WDSP=?,WDSP_COUNT=?,MAX_TEMP=?,MAX_FLAG=?,MIN_TEMP=?,MIN_FLAG=? where ST_NUMBER=? AND WBAN=?";
    private static final String QUERY_STATIONS_BY_DATE = "select ST_NUMBER,YEAR_MODA,WBAN, TEMP,TEMP_COUNT,WDSP,WDSP_COUNT,MAX_TEMP,MAX_FLAG,MIN_TEMP,MIN_FLAG " +
            " from gsod where YEAR_MODA BETWEEN ? AND ?;";
    private static final String QUERY_GSOD_BY_PRIMARY_KEY = "select ST_NUMBER,WBAN,YEAR_MODA, TEMP,TEMP_COUNT,WDSP,WDSP_COUNT,MAX_TEMP,MAX_FLAG,MIN_TEMP,MIN_FLAG \" +\n" +
            "            \" from gsod where ST_NUMBER=? AND WBAN=? ORDER BY ST_NUMBER";
    private static final String QUERY_COUNT_GSOD = "Select count(ST_NUMBER)from gsod;";
    private static final String QUERY_ALL_STATIONS = "SELECT ST_NUMBER,WBAN,YEAR_MODA, TEMP,TEMP_COUNT,WDSP,WDSP_COUNT,MAX_TEMP,MAX_FLAG,MIN_TEMP,MIN_FLAG from gsod";

    private DataSource dataSource;

    public DBGlobalSummaryOfDayRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<GlobalSummaryOfDay> listAllStations() {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_ALL_STATIONS)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    List<GlobalSummaryOfDay> gsodList = new ArrayList<>();
                    while (resultSet.next()) {
                        GlobalSummaryOfDay gsod = new GlobalSummaryOfDay();
                        gsod.setStn(resultSet.getInt("ST_NUMBER"));
                        gsod.setWban(resultSet.getInt("WBAN"));
                        gsod.setDate(resultSet.getInt("YEAR_MODA"));
                        gsod.setTemperature(resultSet.getDouble("TEMP"));
                        gsod.setTempCount(resultSet.getInt("TEMP_COUNT"));
                        gsod.setWindSpeed(resultSet.getDouble("WDSP"));
                        gsod.setWindSpedCount(resultSet.getInt("WDSP_COUNT"));
                        gsodList.add(gsod);
                    }
                    return gsodList;
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public void insert(GlobalSummaryOfDay gs) {
        try {
            try (Connection connection = dataSource.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement(INSERT_GSOD_SQL)) {
                    statement.setInt(1, gs.getStn());
                    statement.setInt(2, gs.getWban());
                    statement.setInt(3, gs.getDate());
                    statement.setDouble(4, gs.getTemperature());
                    statement.setDouble(5, gs.getTempCount());
                    statement.setDouble(6, gs.getWindSpeed());
                    statement.setInt(7, (gs.getWindSpedCount()));
                    statement.setDouble(8, gs.getMaxTemp());
                    statement.setString(9, gs.getMaxTempFlag());
                    statement.setDouble(10, gs.getMinTemp());
                    statement.setString(11, gs.getMinTempFlag());
                    statement.addBatch();
                    statement.executeBatch();
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<GlobalSummaryOfDay> listAllStationsFilterByDate(int fromDate, int toDate) {
        List<GlobalSummaryOfDay> gsodList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_STATIONS_BY_DATE)) {
                preparedStatement.setInt(1, fromDate);
                preparedStatement.setInt(2, toDate);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        GlobalSummaryOfDay gsod = new GlobalSummaryOfDay();
                        gsod.setStn(resultSet.getInt("ST_NUMBER"));
                        gsod.setWban(resultSet.getInt("WBAN"));
                        gsod.setDate(resultSet.getInt("YEAR_MODA"));
                        gsod.setTemperature(resultSet.getDouble("TEMP"));
                        gsod.setTempCount(resultSet.getInt("TEMP_COUNT"));
                        gsod.setWindSpeed(resultSet.getDouble("WDSP"));
                        gsod.setWindSpedCount(resultSet.getInt("WDSP_COUNT"));
                        gsodList.add(gsod);
                    }
                    return gsodList;
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<GlobalSummaryOfDay> StationsFilterByPrimaryKey(int stn, int wban) {
        try (Connection connection = dataSource.getConnection()) {

            try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_GSOD_BY_PRIMARY_KEY)) {
                preparedStatement.setInt(1, stn);
                preparedStatement.setInt(2, wban);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    List<GlobalSummaryOfDay> gsodList = new ArrayList<>();
                    while (resultSet.next()) {
                        GlobalSummaryOfDay gsod = new GlobalSummaryOfDay();
                        gsod.setStn(resultSet.getInt("ST_NUMBER"));
                        gsod.setWban(resultSet.getInt("WBAN"));
                        gsod.setDate(resultSet.getInt("YEAR_MODA"));
                        gsod.setTemperature(resultSet.getDouble("TEMP"));
                        gsod.setTempCount(resultSet.getInt("TEMP_COUNT"));
                        gsod.setWindSpeed(resultSet.getDouble("WDSP"));
                        gsod.setWindSpedCount(resultSet.getInt("WDSP_COUNT"));
                        gsodList.add(gsod);
                    }
                    return gsodList;
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }

    }

    @Override
    public void update(GlobalSummaryOfDay gsod) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(UPDATE_GSOD)) {
                statement.setInt(1, gsod.getDate());
                statement.setDouble(2, gsod.getTemperature());
                statement.setInt(3, gsod.getTempCount());
                statement.setDouble(4, gsod.getWindSpeed());
                statement.setInt(5, gsod.getWindSpedCount());
                statement.setDouble(6, gsod.getMaxTemp());
                statement.setString(7, gsod.getMaxTempFlag());
                statement.setDouble(8, gsod.getMinTemp());
                statement.setString(9, gsod.getMinTempFlag());
                statement.setInt(10, gsod.getStn());
                statement.setInt(11, gsod.getWban());
                int updated = statement.executeUpdate();
                if (updated == 0) {
                    throw new RecordNotFoundException("No rows Were updated: " + gsod.getStn() + "-" + gsod.getWban());
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }


    }

    @Override
    public int count() {
        int count = 0;
        try {
            try (Connection connection = dataSource.getConnection()) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(QUERY_COUNT_GSOD)) {
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
}
