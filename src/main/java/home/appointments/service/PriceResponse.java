package home.appointments.service;

import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
public class PriceResponse {
    Integer priceUnit;
    Integer priceHundredth;

    PriceResponse(@NonNull Integer priceUnit, @NonNull Integer priceHundredth) {
        if(priceUnit < 0 || priceHundredth < 0) {
            throw new IllegalArgumentException("The components of price may not contain elemenets which are less than 0");
        }
        this.priceUnit = priceUnit;
        this.priceHundredth = priceHundredth;
    }

    public static Optional<PriceResponse> from(PriceEntity priceEntity) {
        try {
            return Optional.of(new PriceResponse(priceEntity.getPriceUnit(), priceEntity.getPriceHundredth()));
        } catch (NullPointerException | IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
