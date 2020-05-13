package jo.edu.htu.noaa;

import java.math.BigDecimal;

public class Station {

    private String usaf;
    private String wban;
    private String name;
    private String ctry;
    private String icao;
    private String state;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private BigDecimal ELEV;
    private String begin;
    private String end;


    public BigDecimal getELEV() {
        return ELEV;
    }

    public void setELEV(BigDecimal ELEV) {
        this.ELEV = ELEV;
    }


    public String getCtry() {
        return ctry;
    }

    public void setCtry(String ctry) {
        this.ctry = ctry;
    }

    public String getIcao() {
        return icao;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public String getUsaf() {
        return usaf;
    }

    public void setUsaf(String usaf) {
        this.usaf = usaf;
    }

    public String getWban() {
        return wban;
    }

    public void setWban(String wban) {
        this.wban = wban;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
