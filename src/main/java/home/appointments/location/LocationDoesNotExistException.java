package home.appointments.location;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LocationDoesNotExistException extends ResponseStatusException {
    public LocationDoesNotExistException() {
        super(HttpStatus.NOT_FOUND);
    }
}
