package home.appointments.providers;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception used to represent that the Provider which was requested was not identified. This exception could be thrown
 * for example if we called GET /providers/{id} endpoint for an id which is not related to any provider.
 */
class ProviderNotFoundException extends ResponseStatusException {
    private static final String REASON_STRING = "The Provider resource which you have asked for could not be found. Please call /providers first to obtain the list of all provider resources, and then try to obtain one by ID";
    public ProviderNotFoundException() {
        super(HttpStatus.NOT_FOUND, REASON_STRING);
    }
}
