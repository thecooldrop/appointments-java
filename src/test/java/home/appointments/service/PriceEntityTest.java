package home.appointments.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PriceEntityTest {

    @Test
    void priceUnitCanNotBeNull() {
        assertThrows(NullPointerException.class, () -> new PriceEntity(null, 100));
    }

    @Test
    void priceHundredthCanNotBeNull() {
        assertThrows(NullPointerException.class, () -> new PriceEntity(100, null));
    }

    @Test
    void priceComponentsHaveToBeNonNegative() {
        assertThrows(IllegalArgumentException.class, () -> new PriceEntity(-1, 100));
        assertThrows(IllegalArgumentException.class, () -> new PriceEntity(100, -1));
        assertThrows(IllegalArgumentException.class, () -> new PriceEntity(-1,-1));
    }
}
