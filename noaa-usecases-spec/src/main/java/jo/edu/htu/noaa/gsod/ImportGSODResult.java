package jo.edu.htu.noaa.gsod;

public class ImportGSODResult {

    private int total;
    private int updated;
    private int newRecords;
    private int totalInDatabase;

    public ImportGSODResult(int total, int updated, int newRecords, int totalInDatabase) {
        this.total = total;
        this.updated = updated;
        this.newRecords = newRecords;
        this.totalInDatabase = totalInDatabase;
    }

    public int getUpdated() {
        return updated;
    }

    public int getTotalInDatabase() {
        return totalInDatabase;
    }

    public int getTotal() {
        return total;
    }

    public int getNewRecords() {
        return newRecords;
    }
}
