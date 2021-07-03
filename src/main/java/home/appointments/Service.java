package home.appointments;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "services",
        uniqueConstraints = {@UniqueConstraint(name = "SERVICE_UNIQUE_NAME",
                columnNames = {"name"})})
@EqualsAndHashCode
@ToString
public class Service {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    @NotBlank
    @Length(max = 255)
    private String name;

    // Constructor used by Hibernate
    Service() {
    }

    public Service(@NonNull String name) {
        setName(name);
    }

    public void setName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if(name.isEmpty()) {
            throw new IllegalArgumentException("Paramter name may not be an empty string");
        }
        if(name.isBlank()) {
            throw new IllegalArgumentException("Parameter name may not be a blank string");
        }
        if(name.length() > 255) {
            throw new IllegalArgumentException("Parameter name may not be longer than 255 chars");
        }
    }
}
