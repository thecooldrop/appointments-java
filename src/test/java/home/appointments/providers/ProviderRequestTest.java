package home.appointments.providers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProviderRequestTest {

    @Test
    void canNotCreateProviderRequestWithNullNames() {
        Assertions.assertThrows(NullPointerException.class, () -> new ProviderRequest(null, null));
    }

    @Test
    void canNotCreateProviderRequestWithBlankNames() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ProviderRequest("", "\n\t  "));
    }

    @Test
    void inputsGetConvertedToCanonicalNameRerpesentations() {
        ProviderRequest request = new ProviderRequest("First", "Name");
        Assertions.assertEquals(new ProviderRequest("first", "name"), request);
    }

    @Test
    void canNotCreateProviderWithNameLongerThan128Chars() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ProviderRequest("a".repeat(129), "b"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ProviderRequest("a", "b".repeat(129)));
    }
}
