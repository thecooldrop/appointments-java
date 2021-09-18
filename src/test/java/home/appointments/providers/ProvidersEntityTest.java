package home.appointments.providers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertThrows(IllegalArgumentException.class, () -> new ProviderEntity("vanio", "a".repeat(129)));
    }

    @Test
    public void canNotSetNullFirstName() {
        ProviderEntity entity = new ProviderEntity("vanio", "begic");
        assertThrows(NullPointerException.class, () -> {
            entity.setFirstName(null);
        });
    }

    @Test
    public void canNotSetNullLastName() {
        ProviderEntity entity = new ProviderEntity("vanio", "begic");
        assertThrows(NullPointerException.class, () -> {
            entity.setLastName(null);
        });
    }

    @Test
    public void canNotSetEmptyFirstName() {
        ProviderEntity entity = new ProviderEntity("vanio", "begic");
        assertThrows(IllegalArgumentException.class, () -> {
            entity.setFirstName("");
        });
    }

    @Test
    public void canNotSetBlankFirstName() {
        ProviderEntity entity = new ProviderEntity("vanio", "begic");
        assertThrows(IllegalArgumentException.class, () -> {
            entity.setFirstName("   ");
        });
    }

    @Test
    public void canNotSetEmptyLastName() {
        ProviderEntity entity = new ProviderEntity("vanio", "begic");
        assertThrows(IllegalArgumentException.class, () -> {
            entity.setLastName("");
        });
    }

    @Test
    public void canNotSetBlankLastName() {
        ProviderEntity entity = new ProviderEntity("vanio", "begic");
        assertThrows(IllegalArgumentException.class, () -> {
            entity.setLastName("   ");
        });
    }

    @Test
    public void firstnameCanNotContainUppercaseCharacters() {
        assertThrows(IllegalArgumentException.class, () -> new ProviderEntity("Vanio", "begic"));
    }

    @Test
    public void lastNameCanNotContainUppercaseCharacters() {
        assertThrows(IllegalArgumentException.class, () -> new ProviderEntity("vanio", "Begic"));
    }

    @Test
    public void canConstructProviderEntityFromValidInputs() {
        Optional<ProviderEntity> providerEntity = ProviderEntity.from("vanio", "begic");
        Assertions.assertTrue(providerEntity.isPresent());
    }

    @Test
    public void canNotConstructProviderEntityFromInvalidInputs() {
        Optional<ProviderEntity> nullInputEntity = ProviderEntity.from(null, null);
        Optional<ProviderEntity> blankInputEntity = ProviderEntity.from("", "\n\t  ");
        Optional<ProviderEntity> tooLongNamesEntity = ProviderEntity.from("a".repeat(129), "a".repeat(129));
        assertTrue(nullInputEntity.isEmpty());
        assertTrue(blankInputEntity.isEmpty());
        assertTrue(tooLongNamesEntity.isEmpty());
    }
}
