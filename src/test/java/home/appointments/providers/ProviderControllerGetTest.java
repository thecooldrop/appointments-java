package home.appointments.providers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
public class ProviderControllerGetTest {

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ProviderController providerController;

    List<ProviderEntity> initializeProvidersInDatabase() {
        ProviderEntity first = new ProviderEntity("first", "first");
        ProviderEntity second = new ProviderEntity("second", "second");
        ProviderEntity third = new ProviderEntity("third", "third");
        List<ProviderEntity> providers = Arrays.asList(first, second, third);
        return providerRepository.saveAll(providers);
    }

    @Test
    void successfulRetrievalOfProvidersReturnsAllProviders() {
        List<ProviderEntity> expectedProviders = initializeProvidersInDatabase();
        List<ProviderEntity> returnedProviders = providerController.getProviders().getBody();
        Assertions.assertEquals(expectedProviders, returnedProviders);
    }

    @Test
    void successfulRetrievalOfProvidersRetunsHttpStatusCode200() {
        initializeProvidersInDatabase();
        HttpStatus returnedCode = providerController.getProviders().getStatusCode();
        Assertions.assertEquals(HttpStatus.OK, returnedCode);
    }


    @Test
    void successfulRetrievalOfProviderByIdReturnsTheCorrectProvider() {
        List<ProviderEntity> expectedProviders = initializeProvidersInDatabase();
        for(ProviderEntity expectedProvider : expectedProviders) {
            ResponseEntity<ProviderEntity> returnedProvider = providerController.getProviderById(expectedProvider.getId());
            Assertions.assertEquals(expectedProvider, returnedProvider.getBody());
            Assertions.assertEquals(HttpStatus.OK, returnedProvider.getStatusCode());
        }
    }

    @Test
    void tryingToRetrieveTheProviderByNonexistentIdReturns404HttpStatusCode() {
        Assertions.assertThrows(ProviderNotFoundException.class, () -> providerController.getProviderById(100));
    }
}
