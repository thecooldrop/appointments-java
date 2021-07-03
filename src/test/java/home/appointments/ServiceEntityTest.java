package home.appointments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceEntityTest {

    @Test
    public void serviceNameCanNotBeNull() {
        assertThrows(NullPointerException.class, () -> {
           new Service(null);
        });
    }

    @Test
    public void serviceNameCanNotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new Service(""));
    }

    @Test
    public void serviceNameCanNotBeBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Service("   \n\t"));
    }

    @Test
    public void serviceNameCanNotBeLongerThan255Chars() {
        assertThrows(IllegalArgumentException.class, () -> new Service("a".repeat(256)));
    }

    @Test
    public void serviceCanNotBeChangedToHaveNullName() {
        assertThrows(NullPointerException.class, () -> {
           Service testService = new Service("hand manicure");
           testService.setName(null);
        });
    }

    @Test
    public void serviceCanNotBeChangedToHaveEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> {
            Service testService = new Service("hand manicure");
            testService.setName("");
        });
    }

    @Test
    public void serviceCanNotBeChangedToHaveBlankName() {
        assertThrows(IllegalArgumentException.class, () -> {
            Service testService = new Service("hand manicure");
            testService.setName("  \n\t");
        });
    }

    @Test
    public void serviceCanNotBeChangedToHaveNameLongerThan255Chars() {
        assertThrows(IllegalArgumentException.class, () -> {
            Service testService = new Service("hand manicure");
            testService.setName("a".repeat(256));
        });
    }
}

