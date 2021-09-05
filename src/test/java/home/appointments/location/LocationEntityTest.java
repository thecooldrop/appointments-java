package home.appointments.location;

import org.junit.jupiter.api.Test;

public class LocationEntityTest {

    @Test
    void canCreateLocationEntity() {
        LocationEntity entity = new LocationEntity();
    }

    @Test
    void canNotCreateLocationEntityWithNonUppercaseString() {

    }
}
