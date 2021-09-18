package home.appointments.providers;

import lombok.NonNull;
import lombok.Value;

@Value
public class ProviderRequest {
    @NonNull String firstName;
    @NonNull String lastName;

    public ProviderRequest(@NonNull String firstName, @NonNull String lastName) {
        if(firstName.isBlank() || lastName.isBlank()) {
            throw new IllegalArgumentException("First and last name of the provider may not be null");
        }
        if(firstName.length() > 128 || lastName.length() > 128) {
            throw new IllegalArgumentException("First and last name of the provider may not be longer than 128 characters");
        }
        this.firstName = firstName.toLowerCase();
        this.lastName = lastName.toLowerCase();
    }
}
