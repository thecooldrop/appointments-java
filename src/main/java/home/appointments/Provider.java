package home.appointments;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Provider {

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @NotBlank
    @Length(max = 128, message = "The name of the provider can not exceed 128 characters")
    @Column(name = "name", nullable = false, length = 128)
    private String name;
}
