package home.appointments.service;

import lombok.NonNull;
import lombok.Value;

@Value
class PriceRequest {
    Integer priceKm;
    Integer pricePfening;

    PriceRequest(@NonNull Integer priceKm, @NonNull Integer pricePfening) {
        if(priceKm < 0 || pricePfening < 0) {
            throw new IllegalArgumentException("All of the fields of the price must be be non-negative integers");
        }
        this.priceKm = priceKm;
        this.pricePfening = pricePfening;
    }
}
