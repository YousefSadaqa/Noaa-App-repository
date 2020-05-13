package jo.edu.htu.noaa;

import java.time.LocalDate;

public class GlobalSummaryOfDay {
    private Integer stn;
    private Integer wban;
    private Integer date;
    private Integer tempCount;
    private Double windSpeed;
    private Integer windSpedCount;
    private Double temperature;
    private Double maxTemp;
    private String maxTempFlag;
    private Double minTemp;
    private String minTempFlag;

    public Integer getStn() {
        return stn;
    }

    public void setStn(int stn) {
        this.stn = stn;
    }

    public int getTempCount() {
        return tempCount;
    }

    public void setTempCount(int tempCount) {
        this.tempCount = tempCount;
    }

    public int getWindSpedCount() {
        return windSpedCount;
    }

    public void setWindSpedCount(int windSpedCount) {
        this.windSpedCount = windSpedCount;
    }

    public String getMaxTempFlag() {
        return maxTempFlag;
    }

    public void setMaxTempFlag(String maxTempFlag) {
        this.maxTempFlag = maxTempFlag;
    }

    public String getMinTempFlag() {
        return minTempFlag;
    }

    public void setMinTempFlag(String minTempFlag) {
        this.minTempFlag = minTempFlag;
    }

    public Integer getWban() {
        return wban;
    }

    public void setWban(int wban) {
        this.wban = wban;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
}
