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

    ProviderEntity(@NonNull String firstName, @NonNull String lastName) {
        NameKind.FIRST.validate(firstName);
        NameKind.LAST.validate(lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    void setFirstName(@NonNull String firstName) {
        NameKind.FIRST.validate(firstName);
        this.firstName = firstName;
    }

    void setLastName(@NonNull String lastName) {
        NameKind.LAST.validate(lastName);
        this.lastName = lastName;
    }

    private enum NameKind {
        FIRST {
            public void validate(String name) {
                if(name.isBlank()) {
                    throw new IllegalArgumentException("The first name of the provider can not be blank");
                }
                if(name.length() > 128) {
                    throw new IllegalArgumentException("The first name of the provider can not be longer than 128 charachters");
                }
            }
        },
        LAST {
            public void validate(String name) {
                if(name.isBlank()) {
                    throw new IllegalArgumentException("The second name of the provider can not be blank");
                }
                if(name.length() > 128) {
                    throw new IllegalArgumentException("The second name of the provider can not be longer than 128 charachters");
                }
            }
        };

        abstract void validate(String firstName);
    }
}
