package home.appointments.providers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.util.Optional;


/**
 * This class is used to represent the providers which may provide certain services. This represents an actual person
 * which can provide some services at specific location.
 */
@EqualsAndHashCode
@ToString
@Entity
@Getter
@Table(name = "providers")
class ProviderEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    @Length(max=128)
    private String firstName;

    @NotBlank
    @Length(max=128)
    private String lastName;

    // Constructor only for Hibernate
    ProviderEntity() {
    }

    /**
     * Enables construction of Provider objects with specific constraints. The arguments passed in may not be null
     * @param firstName - The first name of the provider
     * @param lastName - The last name of the provider
     * @throws NullPointerException - if first name or last name is null
     * @throws IllegalArgumentException - if either first name or last name is empty, blank or exceeds 128 characters in
     * length.
     */
    ProviderEntity(@NonNull String firstName, @NonNull String lastName) {
        validateNames(firstName, lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Enables changing the first name of specific provider in case of name change. Should not be used for any other
     * purposes
     * @param firstName - the new name of the provider
     * @throws NullPointerException - if the passed in argument is null
     * @throws IllegalArgumentException - if passed in argument is empty, blank or longer than 128 characters
     */
    void setFirstName(@NonNull String firstName) {
        validateName(firstName, NameKind.FIRST);
        this.firstName = firstName;
    }

    /**
     * Enables changing the last name of specific provider in case of name change. Should not be used for any other
     * purposes
     * @param lastName - the new name of the provider.
     * @throws NullPointerException - if the passed in argument is null
     * @throws IllegalArgumentException - if passed in argument is empty, blank or longer than 128 characters
     */
    void setLastName(@NonNull String lastName) {
        validateName(lastName, NameKind.LAST);
        this.lastName = lastName;
    }

    private static void validateNames(String firstName, String lastName) {
        validateName(firstName, NameKind.FIRST);
        validateName(lastName, NameKind.LAST);
    }

    private static void validateName(String name, NameKind nameKind) {
        if(name.isBlank()) {
            throw new IllegalArgumentException("The " + nameKind.name() + " name of the provider can not be blank");
        }
        if(name.length() > 128) {
            throw new IllegalArgumentException("The " + nameKind.name() + " name of the provider can not be longer than 128 charachters");
        }
        if(!name.equals(name.toLowerCase())) {
            throw new IllegalArgumentException("The " + nameKind.name() + " name of the provider can not contain uppercase letters");
        }
    }

    public static Optional<ProviderEntity> from(String firstName, String lastName) {
        try {
            validateNames(firstName, lastName);
            return Optional.of(new ProviderEntity(firstName, lastName));
        } catch (IllegalArgumentException | NullPointerException e) {
            return Optional.empty();
        }
    }

    private enum NameKind {
        FIRST, LAST
    }
}
