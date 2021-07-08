package home.appointments.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

class ServiceNotFoundException extends ResponseStatusException {
    private static final String REASON_STRING = "The Service resource which you have asked for could not be found. Please call /services first to obtain the list of all service resources, and then try to obtain one by ID";
    public ServiceNotFoundException() {
        super(HttpStatus.NOT_FOUND, REASON_STRING);
    }
}
