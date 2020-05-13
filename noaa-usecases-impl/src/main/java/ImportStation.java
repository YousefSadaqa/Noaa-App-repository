import jo.edu.htu.noaa.Station;
import jo.edu.htu.noaa.stations.ImportStationsHandler;
import jo.edu.htu.noaa.stations.ImportStationsRequest;
import jo.edu.htu.noaa.stations.ImportStationsResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImportStation implements ImportStationsHandler {
    private StationRepository repository;
    int totalParse = 0;
    int updatedRecords = 0;
    int newRecords = 0;
    int totalInDatabase = 0;

    public ImportStation(StationRepository repository) {
        this.repository = repository;
    }

    @Override
    public ImportStationsResult importStations(ImportStationsRequest request) {
        Path filePath = request.getStationsPath();
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            skipingEmptyLines(reader);
            while ((line = reader.readLine()) != null) {
                Station station = getFieldsAsStation(line);
                processRecord(station);
            }
            totalInDatabase = repository.count();
            return new ImportStationsResult(totalParse, updatedRecords, newRecords, totalInDatabase);
        } catch (IOException e) {
            throw new ImportException(e);
        }

    }

    private void processRecord(Station station) {
        totalParse++;
        try {
            updatedRecords++;
            System.out.println("update " + station.getUsaf() + " " + station.getWban());
            repository.update(station);
        } catch (RecordNotFoundException e) {
            newRecords++;
            System.out.println("insert " + station.getUsaf() + " " + station.getWban());
            repository.insert(station);
        }

    }

    private void skipingEmptyLines(BufferedReader reader) throws IOException {
        String skipLine1 = reader.readLine();
        String SkipLine2 = reader.readLine();
    }

    private Station getFieldsAsStation(String line) {
        Station station = new Station();
        station.setUsaf(line.substring(0, 7));
        station.setWban(line.substring(7, 13));
        station.setName(line.substring(13, 43));
        station.setCtry(line.substring(43, 47));
        station.setState(line.substring(48, 50));
        station.setIcao(line.substring(51, 56));
        station.setLatitude(parseBigDecimal(line.substring(57, 64)));
        station.setLongitude(parseBigDecimal(line.substring(65, 73)));
        station.setELEV(parseBigDecimal(line.substring(74, 81)));
        String begin = line.substring(82, 90);
        station.setBegin(begin);
        String end = line.substring(91, 99);
        station.setEnd(end);
        return station;
    }

    private BigDecimal parseBigDecimal(String strNumber) {
        if (strNumber.isEmpty() || strNumber.contains(" ")) {
            return null;
        }
        return new BigDecimal(strNumber);
    }


}