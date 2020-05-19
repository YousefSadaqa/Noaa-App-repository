package jo.edu.htu.noaa;

import jo.edu.htu.DBGlobalSummaryOfDayRepository;
import jo.edu.htu.noaa.gsod.ListGSODHandler;
import jo.edu.htu.noaa.gsod.ListGSODRequest;
import jo.edu.htu.noaa.gsod.ListGSODResult;

public class ListGSOD implements ListGSODHandler {
    private DBGlobalSummaryOfDayRepository repository;

    public ListGSOD(DBGlobalSummaryOfDayRepository repository) {
        this.repository = repository;
    }

    @Override
    public ListGSODResult list(ListGSODRequest request) {
        if (request.getReadingDateFrom() != null || request.getGetReadingDateTo() != null) {
            int fromDate = Integer.parseInt(String.valueOf(request.getReadingDateFrom()));
            int toDate = Integer.parseInt(String.valueOf(request.getGetReadingDateTo()));
            return new ListGSODResult(repository.listAllStationsFilterByDate(fromDate, toDate));
        }
        if (request.getUsaf() != null || request.getWban() != null)
            return new ListGSODResult(repository.StationsFilterByPrimaryKey(Integer.parseInt(request.getUsaf()), Integer.parseInt(request.getWban())));

        return new ListGSODResult(repository.listAllStations());
    }
}

