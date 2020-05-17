package jo.edu.htu.noaa;

import java.io.IOException;

public class ImportException extends RuntimeException {
    public ImportException(Exception e) {
        super(e);
    }
}
