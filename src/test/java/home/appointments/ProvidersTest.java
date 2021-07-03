package home.appointments;

import home.appointments.Provider;
import home.appointments.ProviderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.util.List;

@SpringBootTest
public class ProvidersTest {

    @Autowired
    private ProviderRepository providersAccess;

    @Test
    public void canInsertNewProvider() {
        Provider firstProvider = new Provider();
        firstProvider.setName("Vanio");
        providersAccess.save(firstProvider);
        List<Provider> providers = providersAccess.findAll();
        Assertions.assertEquals(firstProvider, providers.get(0), "Providers not equal");
    }

    @Test
    public void providerNameCanNotBeNull() {
        try {
            Provider firstProvider = new Provider();
            firstProvider.setName(null);
            providersAccess.save(firstProvider);
        } catch(Exception e) {
            while(e.getCause() != null) {
                if(e.getCause() instanceof ConstraintViolationException) {
                    if(e.getCause().getMessage().contains("must not be null")) {
                        break;
                    }
                    Assertions.fail();
                }
                if(e.getCause() instanceof Exception) {
                    e = (Exception) e.getCause();
                    continue;
                }
                Assertions.fail();
            }
        }
    }
}
