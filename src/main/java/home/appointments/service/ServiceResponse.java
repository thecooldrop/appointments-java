package home.appointments.service;

import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
public class ServiceResponse {
    Integer id;
    String name;
    String description;
    PriceResponse servicePrice;
    Integer durationMinutes;

    ServiceResponse(@NonNull Integer id,
                    @NonNull String name,
                    @NonNull String description,
                    @NonNull PriceResponse servicePrice,
                    @NonNull Integer durationMinutes) {

        if(name.isBlank()) {
            throw new IllegalArgumentException("Name of the service may not be blank");
        }
        if(name.length() > 255) {
            throw new IllegalArgumentException("The name of the service may not contain more than 255 characters");
        }
        if(durationMinutes <= 0) {
            throw new IllegalArgumentException("The duration of the service must be positive integer");
        }
        this.id = id;
        this.name = name;
        this.description = description;
        this.servicePrice = servicePrice;
        this.durationMinutes = durationMinutes;
    }

    public static Optional<ServiceResponse> from(ServiceEntity service) {
        try {
            PriceResponse
        }
    }
}
