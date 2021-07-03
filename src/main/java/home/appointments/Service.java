package home.appointments;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "services", uniqueConstraints = {@UniqueConstraint(name = "SERVICE_UNIQUE_NAME", columnNames = {"name"})})
@Data
@RequiredArgsConstructor
public class Service {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    @NotNull
    @Length(max = 255)
    private String name;
}
