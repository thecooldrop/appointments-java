package home.appointments.location;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocationNameTest {

    @Test
    void locationNameCanNotBeNull() {
        assertThrows(NullPointerException.class, () -> new LocationName(null));
    }

    @Test
    void locationNameCanNotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new LocationName(""));
    }

    @Test
    void locationNameCanNotBeBlank() {
        assertThrows(IllegalArgumentException.class, () -> new LocationName("\n\t  "));
    }

    @Test
    void locationNameCanNotBeLongerThan255Characters() {
        assertThrows(IllegalArgumentException.class, () -> new LocationName("a".repeat(256)));
    }

    @Test
    void validLocationNameIsSavedInCanonicalFormat() {
        assertEquals(new LocationName("location"), new LocationName("LocaTIon"));
    }
}
