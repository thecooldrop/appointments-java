package home.appointments.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceEntityTest {

    @Test
    public void serviceNameCanNotBeNull() {
        assertThrows(NullPointerException.class, () -> {
           new ServiceEntity(null);
        });
    }

    @Test
    public void serviceNameCanNotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new ServiceEntity(""));
    }

    @Test
    public void serviceNameCanNotBeBlank() {
        assertThrows(IllegalArgumentException.class, () -> new ServiceEntity("   \n\t"));
    }

    @Test
    public void serviceNameCanNotBeLongerThan255Chars() {
        assertThrows(IllegalArgumentException.class, () -> new ServiceEntity("a".repeat(256)));
    }

    @Test
    public void serviceCanNotBeChangedToHaveNullName() {
        assertThrows(NullPointerException.class, () -> {
           ServiceEntity testServiceEntity = new ServiceEntity("hand manicure");
           testServiceEntity.setName(null);
        });
    }

    @Test
    public void serviceCanNotBeChangedToHaveEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> {
            ServiceEntity testServiceEntity = new ServiceEntity("hand manicure");
            testServiceEntity.setName("");
        });
    }

    @Test
    public void serviceCanNotBeChangedToHaveBlankName() {
        assertThrows(IllegalArgumentException.class, () -> {
            ServiceEntity testServiceEntity = new ServiceEntity("hand manicure");
            testServiceEntity.setName("  \n\t");
        });
    }

    @Test
    public void serviceCanNotBeChangedToHaveNameLongerThan255Chars() {
        assertThrows(IllegalArgumentException.class, () -> {
            ServiceEntity testServiceEntity = new ServiceEntity("hand manicure");
            testServiceEntity.setName("a".repeat(256));
        });
    }
}

