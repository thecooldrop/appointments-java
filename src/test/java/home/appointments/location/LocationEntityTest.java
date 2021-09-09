package home.appointments.location;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LocationEntityTest {

    @Test
    void canCreateLocationEntity() {
        new LocationEntity();
    }

    @Test
    void canNotCreateLocationEntityWithStringsContainingUppercaseLetters() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new LocationEntity("Uppercase location");
        });
    }

    @Test
    void canNotCreateLocationEntityWithNullStrings() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            new LocationEntity(null);
        });
    }

    @Test
    void canNotCreateLocationEntityWithEmptyOrBlankStrings() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new LocationEntity("");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new LocationEntity("\n\t  ");
        });
    }

    @Test
    void canNotContainMoreThan255Characters() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
           new LocationEntity("a".repeat(256));
        });
    }
}
