package jo.edu.htu.noaa.stations;

public class ImportStationsResult {

    private int total;
    private int updated;
    private int newRecords;
    private int totalInDatabase;

    public ImportStationsResult(int total, int updated, int newRecords, int totalInDatabase) {
        this.total = total;
        this.updated = updated;
        this.newRecords = newRecords;
        this.totalInDatabase = totalInDatabase;
    }

    public int getTotalInDatabase() {
        return totalInDatabase;
    }

    public int getTotal() {
        return total;
    }

    public int getUpdated() {
        return updated;
    }

    public int getNewRecords() {
        return newRecords;
    }
}
