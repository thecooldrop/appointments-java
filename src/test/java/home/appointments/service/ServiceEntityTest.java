package home.appointments.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceEntityTest {

    @Test
    public void serviceNameCanNotBeNull() {
        assertThrows(NullPointerException.class, () -> new ServiceEntity(null, "Some service for some price", 30,  new PriceEntity()));
    }

    @Test
    public void serviceDescriptionCanNotBeNull() {
        assertThrows(NullPointerException.class, () -> new ServiceEntity("Some service", null, 30, new PriceEntity()));
    }

    @Test
    public void durationCanNotBeNull() {
        assertThrows(NullPointerException.class, () -> new ServiceEntity("Some Service", "Some description", null, new PriceEntity()));
    }

    @Test
    public void durationCanNotBeNonPositive() {
        assertThrows(IllegalArgumentException.class, () -> new ServiceEntity("Some service", "Some description", 0, new PriceEntity()));
        assertThrows(IllegalArgumentException.class, () -> new ServiceEntity("Some service", "Some description", -1, new PriceEntity()));
    }

    @Test
    public void serviceNameCanNotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new ServiceEntity("", "Some description", 30, new PriceEntity()));
    }

    @Test
    public void serviceNameCanNotBeBlank() {
        assertThrows(IllegalArgumentException.class, () -> new ServiceEntity("   \n\t", "Some description", 30, new PriceEntity()));
    }

    @Test
    public void serviceNameCanNotBeLongerThan255Chars() {
        assertThrows(IllegalArgumentException.class, () -> new ServiceEntity("a".repeat(256), "Some description", 30, new PriceEntity()));
    }

    @Test
    public void serviceMustHaveANonNullPrice() {
        assertThrows(NullPointerException.class, () -> new ServiceEntity("Some service", "Some description", 30, null));
    }
}

