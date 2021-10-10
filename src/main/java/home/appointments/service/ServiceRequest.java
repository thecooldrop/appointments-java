package home.appointments.service;

import lombok.NonNull;
import lombok.Value;

@Value
public class ServiceRequest {
    String name;
    String description;
    Integer duration;
    PriceRequest price;

    public ServiceRequest(@NonNull String name, @NonNull String description, @NonNull Integer duration, @NonNull PriceRequest price) {
        if(name.isBlank()) {
            throw new IllegalArgumentException("The service name may not be null");
        }
        if(name.length() > 255) {
            throw new IllegalArgumentException("The name of the service may not exceed 255 characters");
        }
        if(duration < 0 ) {
            throw new IllegalArgumentException("The duration of the service has to be a non-negative integer");
        }
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.price = price;
    }
}
