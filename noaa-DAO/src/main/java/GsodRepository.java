import jo.edu.htu.noaa.GlobalSummaryOfDay;
import jo.edu.htu.noaa.Station;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface GsodRepository {

    List<GlobalSummaryOfDay> listAllStations();

    void insert(GlobalSummaryOfDay gs);

    List<GlobalSummaryOfDay> listAllStationsFilterByDate(int fromDate, int toDate);

    List<GlobalSummaryOfDay> StationsFilterByPrimaryKey(int stn, int wban);

    void update(GlobalSummaryOfDay gsod);

    int count();


}
