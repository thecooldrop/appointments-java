package home.appointments.location;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidLocationNameException extends ResponseStatusException {
    public InvalidLocationNameException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
