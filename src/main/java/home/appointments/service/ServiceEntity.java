package home.appointments.service;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * Represents the services which may be provided by the business utilizing this application. For example if application
 * is used by a hair salon an example of service would be "haircut".
 */
@Entity
@Table(name = "services",
        uniqueConstraints = {
            @UniqueConstraint(name = "SERVICE_UNIQUE_NAME", columnNames = {"name"}),
            @UniqueConstraint(name = "SERVICE_UNIQUE_PRICE", columnNames = {"service_price_id"}),
        })
@EqualsAndHashCode
@ToString
@Getter
class ServiceEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotBlank
    @Length(max = 255)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "duration_minutes", nullable = false)
    @Positive
    private Integer durationMinutes;

    @OneToOne
    @JoinColumn(name = "service_price_id")
    private PriceEntity servicePrice;


    /**
     * Constructor only for usage by Hibernate, should not be used anywhere else
     */
    ServiceEntity() {
    }

    /**
     * Creates an object which represents a service offered by some business.
     *
     * The input parameter needs to be a non-null, non-empty, non-blank String of length up to 255 characters.
     * @param name - the name of the Service which we would like to provide
     * @throws NullPointerException - if input parameter is null
     * @throws IllegalArgumentException - if input parameter is empty, blank or exceeds 255 characters
     */
    public ServiceEntity(@NonNull String name, @NonNull String description, @NonNull Integer durationMinutes, @NonNull PriceEntity servicePrice) {
        if(name.isBlank()) {
            throw new IllegalArgumentException("Parameter name may not be empty or blank string");
        }
        if(name.length() > 255) {
            throw new IllegalArgumentException("Parameter name may not be longer than 255 characters");
        }
        if(durationMinutes <= 0) {
            throw new IllegalArgumentException("Parameter durationMinutes must be positive integer");
        }
        this.name = name;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.servicePrice = servicePrice;
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
