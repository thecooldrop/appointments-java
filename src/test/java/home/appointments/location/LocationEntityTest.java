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

    @Test
    void staticFromMethodReturnsAFilledOptionalIfWeTryToConstructFromValidLocationName() {
        Assertions.assertTrue(LocationEntity.from("location").isPresent());
    }

    @Test
    void staticMethodReturnsEmptyOptionalIfWeTryToConstrucFromInvalidValue() {
        Assertions.assertTrue(LocationEntity.from(null).isEmpty());
        Assertions.assertTrue(LocationEntity.from("").isEmpty());
        Assertions.assertTrue(LocationEntity.from("\n\t").isEmpty());
        Assertions.assertTrue(LocationEntity.from("a".repeat(256)).isEmpty());
    }
}
