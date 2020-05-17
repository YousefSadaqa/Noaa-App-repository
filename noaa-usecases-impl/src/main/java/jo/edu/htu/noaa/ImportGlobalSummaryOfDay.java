package jo.edu.htu.noaa;

import jo.edu.htu.GsodRepository;
import jo.edu.htu.RecordNotFoundException;
import jo.edu.htu.noaa.gsod.ImportGSODHandler;
import jo.edu.htu.noaa.gsod.ImportGSODRequest;
import jo.edu.htu.noaa.gsod.ImportGSODResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImportGlobalSummaryOfDay implements ImportGSODHandler {
    private GsodRepository repository;
    int totalParse = 0;
    int updatedRecords = 0;
    int newRecords = 0;
    int totalInDatabase = 0;

    public ImportGlobalSummaryOfDay(GsodRepository repository) {
        this.repository = repository;
    }

    @Override
    public ImportGSODResult importGSOD(ImportGSODRequest request) {
        Path filePath = request.getStationsPath();
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            String Skip1 = reader.readLine();
            while ((line = reader.readLine()) != null) {
                GlobalSummaryOfDay gsod = getFieldsAsGsod(line);
                processRecord(gsod);
            }
            totalInDatabase = repository.count();
            return new ImportGSODResult(totalParse, updatedRecords,
                    newRecords, totalInDatabase);
        } catch (IOException e) {
            throw new ImportException(e);
        }
    }

    private void processRecord(GlobalSummaryOfDay gsod) {
        totalParse++;
        try {
            updatedRecords++;
            System.out.println("update " + gsod.getStn() + " " + gsod.getWban());
            repository.update(gsod);
        } catch (RecordNotFoundException e) {
            newRecords++;
            System.out.println("insert " + gsod.getStn() + " " + gsod.getWban());
            repository.insert(gsod);
        }
    }

    private GlobalSummaryOfDay getFieldsAsGsod(String line) {
        GlobalSummaryOfDay gsod = new GlobalSummaryOfDay();
        gsod.setStn(Integer.parseInt(line.substring(0, 6)));
        gsod.setWban(Integer.parseInt(line.substring(7, 12)));
        String year = line.substring(14, 18);
        String MODA = line.substring(19, 22);
        gsod.setDate(Integer.parseInt(year + MODA));
        gsod.setTemperature(Double.parseDouble(line.substring(25, 30)));
        gsod.setTempCount(parseInt(line.substring(31, 33)));
        gsod.setWindSpeed(Double.parseDouble(line.substring(79, 83)));
        gsod.setWindSpedCount(parseInt(line.substring(84, 86)));
        gsod.setMaxTemp(Double.parseDouble(line.substring(103, 108)));
        gsod.setMaxTempFlag(line.substring(108, 109));
        gsod.setMinTemp(Double.parseDouble(line.substring(111, 116)));
        gsod.setMinTempFlag(line.substring(116, 117));
        return gsod;
    }

    private int parseInt(String intStr) {
        if (intStr.startsWith(" ")) {
            String c = String.valueOf(intStr.charAt(1));
            return Integer.parseInt(c);

        }
        return Integer.parseInt(intStr);
    }
}
