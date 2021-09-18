package home.appointments.providers;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidProviderNames extends ResponseStatusException {

    public InvalidProviderNames() {
        super(HttpStatus.BAD_REQUEST, "The provided provider names are not properly formatted, please try again");
    }
}
