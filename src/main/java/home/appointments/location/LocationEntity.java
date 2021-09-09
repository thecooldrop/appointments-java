package home.appointments.location;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "locations", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})})
@Getter(AccessLevel.PACKAGE)
class LocationEntity {

    @NotNull
    @Column(name = "name", nullable = false, length = 255, unique = true)
    @Length(min = 0, max=255)
    private String name;

    @Id
    @GeneratedValue
    private Long id;

    public LocationEntity() {}
    public LocationEntity(@NonNull String locationName) {
        if(!locationName.equals(locationName.toLowerCase())) {
            throw new IllegalArgumentException("Argument locationName contains uppercase letters!");
        }
        if(locationName.isBlank()) {
            throw new IllegalArgumentException("Argument locationName contains a blank string!");
        }
        if(locationName.isEmpty()) {
            throw new IllegalArgumentException("Argument locationName contains an empty string!");
        }
        if(locationName.length() > 255) {
            throw new IllegalArgumentException("Argument locationName can not contain more than 255 characters");
        }
        name = locationName;
    }
}
