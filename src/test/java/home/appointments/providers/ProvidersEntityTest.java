package home.appointments.providers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProvidersEntityTest {

    @Test
    public void providerFirstNameCanNotBeNull() {
        assertThrows(NullPointerException.class, () -> new ProviderEntity(null, "Begic"));
    }

    @Test
    public void providerLastNameCanNotBeNull() {
        assertThrows(NullPointerException.class, () -> new ProviderEntity("Vanio", null));
    }

    @Test
    public void providerFirstNameCanNotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new ProviderEntity("", "Begic"));
    }

    @Test
    public void providerLastNameCanNotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new ProviderEntity("Vanio", ""));
    }

    @Test
    public void providerFirstNameCanNotBeBlank() {
        assertThrows(IllegalArgumentException.class, () -> new ProviderEntity("  ", "Begic"));
    }

    @Test
    public void providerLastNameCanNotBeBlank() {
        assertThrows(IllegalArgumentException.class, () -> new ProviderEntity("Vanio", "   \n\t"));
    }

    @Test
    public void providerFirstNameCanNotBeLongerThan128Chars() {
        assertThrows(IllegalArgumentException.class, () -> new ProviderEntity("a".repeat(129), "Begic"));
    }

    @Test
    public void providerLastNameCanNotBeLongerThan128Chars() {
        assertThrows(IllegalArgumentException.class, () -> new ProviderEntity("Vanio", "a".repeat(129)));
    }

    @Test
    public void canNotSetNullFirstName() {
        ProviderEntity entity = new ProviderEntity("Vanio", "Begic");
        assertThrows(NullPointerException.class, () -> {
            entity.setFirstName(null);
        });
    }

    @Test
    public void canNotSetNullLastName() {
        ProviderEntity entity = new ProviderEntity("Vanio", "Begic");
        assertThrows(NullPointerException.class, () -> {
            entity.setLastName(null);
        });
    }

    @Test
    public void canNotSetEmptyFirstName() {
        ProviderEntity entity = new ProviderEntity("Vanio", "Begic");
        assertThrows(IllegalArgumentException.class, () -> {
            entity.setFirstName("");
        });
    }

    @Test
    public void canNotSetBlankFirstName() {
        ProviderEntity entity = new ProviderEntity("Vanio", "Begic");
        assertThrows(IllegalArgumentException.class, () -> {
            entity.setFirstName("   ");
        });
    }

    @Test
    public void canNotSetEmptyLastName() {
        ProviderEntity entity = new ProviderEntity("Vanio", "Begic");
        assertThrows(IllegalArgumentException.class, () -> {
            entity.setLastName("");
        });
    }

    @Test
    public void canNotSetBlankLastName() {
        ProviderEntity entity = new ProviderEntity("Vanio", "Begic");
        assertThrows(IllegalArgumentException.class, () -> {
            entity.setLastName("   ");
        });
    }
}
