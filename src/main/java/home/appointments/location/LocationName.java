package home.appointments.location;

import lombok.NonNull;
import lombok.Value;

import java.util.Locale;

@Value
public class LocationName {
    String name;

    public LocationName(@NonNull String locationName) {
        if(locationName.isBlank()) {
            throw new IllegalArgumentException("Location name may not be an empty or blank string");
        }
        if(locationName.length() > 255) {
            throw new IllegalArgumentException("The length of location name may not be longer than 255 characters");
        }
        this.name = locationName.toLowerCase();
    }
}
