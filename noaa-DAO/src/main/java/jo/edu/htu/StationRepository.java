package jo.edu.htu;

import jo.edu.htu.noaa.Station;

import java.math.BigDecimal;
import java.util.List;

public interface StationRepository {
    void insert(Station stations);

    List<Station> listAllStations();

    List<Station> listAllStationsFilterByLocation(BigDecimal lat, BigDecimal lon);

    List<Station> StationsFilterByPrimary(String usaf, int wban);

    void update(Station station);

    int count();


}
