package home.appointments.location;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Entity
@Table(name = "locations", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@Getter(AccessLevel.PACKAGE)
@EqualsAndHashCode
class LocationEntity {

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    @Length(max=255)
    private String name;

    @Id
    @GeneratedValue
    private Long id;

    LocationEntity() {}
    LocationEntity(@NonNull String locationName) {
        if(!locationName.equals(locationName.toLowerCase())) {
            throw new IllegalArgumentException("Argument locationName contains uppercase letters!");
        }
        if(locationName.isBlank()) {
            throw new IllegalArgumentException("Argument locationName contains a blank string!");
        }
        if(locationName.length() > 255) {
            throw new IllegalArgumentException("Argument locationName can not contain more than 255 characters");
        }
        name = locationName;
    }

    /**
     * Creates a new LocationEntity instance if the location name is a valid location name, otherwise it returns
     * an empty optional. Location name is valid if it is:
     * - Not null
     * - Not blank
     * - Not empty string
     * - Not strictly longer than 255 characters
     * - made up of strictly lowercase characters
     * @param locationName - the name of the location
     * @return maybe a location maybe empty Optional
     */
    public static Optional<LocationEntity> from(String locationName) {
        try {
            return Optional.of(new LocationEntity(locationName));
        } catch (IllegalArgumentException | NullPointerException e) {
            return Optional.empty();
        }
    }
}
