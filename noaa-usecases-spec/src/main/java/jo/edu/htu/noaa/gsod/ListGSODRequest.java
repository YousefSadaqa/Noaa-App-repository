package jo.edu.htu.noaa.gsod;

import java.time.LocalDate;

public class ListGSODRequest {

    private String usaf;
    private String wban;
    private String readingDateFrom;
    private String getReadingDateTo;

    public ListGSODRequest(String usaf, String wban, String readingDateFrom, String getReadingDateTo) {
        this.usaf = usaf;
        this.wban = wban;
        this.readingDateFrom = readingDateFrom;
        this.getReadingDateTo = getReadingDateTo;
    }

    public String getUsaf() {
        return usaf;
    }

    public String getWban() {
        return wban;
    }

    public String getGetReadingDateTo() {
        return getReadingDateTo;
    }

    public String getReadingDateFrom() {
        return readingDateFrom;
    }
}
