package home.appointments;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The Service resource which you have asked for could not be found. Please call /services first to obtain the list of all service resources, and then try to obtain one by ID")
class ServiceNotFoundException extends RuntimeException{
}
