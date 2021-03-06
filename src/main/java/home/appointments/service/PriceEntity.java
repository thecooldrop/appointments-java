package home.appointments.service;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "price")
@EqualsAndHashCode
@ToString
@Getter
class PriceEntity {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "price_unit", nullable = false)
    @PositiveOrZero
    @NotNull
    private Integer priceUnit;

    @Column(name = "price_hundredth", nullable = false)
    @PositiveOrZero
    @NotNull
    private Integer priceHundredth;

    // Constructor only for usage by Hibernate
    PriceEntity() {

    }

    PriceEntity(@NonNull Integer priceUnit, @NonNull Integer priceHundredth) {
        if(priceUnit < 0) {
            throw new IllegalArgumentException("The number of price units has to be a nonnegative integer");
        }
        if(priceHundredth < 0) {
            throw new IllegalArgumentException("The number of price hundredths has to be a nonnegative integer");
        }
        this.priceUnit = priceUnit;
        this.priceHundredth = priceHundredth;
    }
}
