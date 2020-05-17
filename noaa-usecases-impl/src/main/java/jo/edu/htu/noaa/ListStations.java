package jo.edu.htu.noaa;

import jo.edu.htu.DBStationRepository;
import jo.edu.htu.noaa.stations.ListStationsHandler;
import jo.edu.htu.noaa.stations.ListStationsRequest;
import jo.edu.htu.noaa.stations.ListStationsResult;

import java.math.BigDecimal;

public class ListStations implements ListStationsHandler {
    private DBStationRepository repository;

    public ListStations(DBStationRepository repository) {
        this.repository = repository;
    }

    @Override
    public ListStationsResult list(ListStationsRequest request) {
        if (request.getLatitude() != null || request.getLongitude() != null) {
            BigDecimal latitude = BigDecimal.valueOf(request.getLatitude());
            BigDecimal longitude = BigDecimal.valueOf(request.getLongitude());
            return new ListStationsResult(repository.listAllStationsFilterByLocation(latitude, longitude));
        }
        if (request.getUsaf() != null || request.getWban() != null) {
            String usaf = request.getUsaf();
            int wban = Integer.parseInt(request.getWban());
            return new ListStationsResult(repository.StationsFilterByPrimary(usaf, wban));
        }
        return new ListStationsResult(repository.listAllStations());
    }


}
