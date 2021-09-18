package home.appointments.providers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;

@SpringBootTest
@Transactional
public class ProviderControllerPostTest {

    @Autowired
    private ProviderRepository providerRepository;

    @Autowired
    private ProviderController providerController;

    @Test
    void successfulCreationOfNewProviderSavesTheProviderIntoDatabase() {
        ResponseEntity<ProviderEntity> createdProviderEntity = providerController.post(new ProviderRequest("first", "second"));
        ProviderEntity createdProvider = createdProviderEntity.getBody();

        Assertions.assertEquals("first", createdProvider.getFirstName());
        Assertions.assertEquals("second", createdProvider.getLastName());
        Assertions.assertEquals(HttpStatus.CREATED, createdProviderEntity.getStatusCode());
        Assertions.assertEquals(providerRepository.getById(createdProvider.getId()), createdProvider);
    }

    @Test
    void successfulPostRequestsIncludeProperLocationHeader() {
        ResponseEntity<ProviderEntity> createdProviderEntity = providerController.post(new ProviderRequest("vanio", "begic"));
        HttpHeaders responseHeaders = createdProviderEntity.getHeaders();
        Assertions.assertTrue(responseHeaders.containsKey(HttpHeaders.LOCATION));
        Assertions.assertEquals(responseHeaders.getLocation(), URI.create("/providers/"+createdProviderEntity.getBody().getId()));
    }

    @Test
    void creatingAProviderWithInvalidNamesLeadsToAnExceptionWithStatusCode400() {
        ProviderRequest req = new ProviderRequest("a".repeat(129),"b");
        try {
            providerController.post(req);
        } catch (InvalidProviderNames e) {
            Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        }
    }
}
