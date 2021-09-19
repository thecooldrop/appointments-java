package home.appointments.providers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
public class ProviderControllerDeleteTest {

    @Autowired
    private ProviderRepository repository;

    @Autowired
    private ProviderController controller;

    @BeforeEach
    void instantiateProvidersInDb() {
        ProviderEntity first = new ProviderEntity("first", "name");
        ProviderEntity second = new ProviderEntity("second", "name-second");
        ProviderEntity third = new ProviderEntity("third", "name-third");
        repository.saveAll(Arrays.asList(first, second, third));
    }

    @Test
    void httpDeleteRemovesTheProviderFromDatabase() {
        List<ProviderEntity> providersBeforeDelete = repository.findAll();
        ProviderEntity providerToDelete = providersBeforeDelete.get(0);

        ResponseEntity<ProviderEntity> deletedProvider = controller.deleteById(providerToDelete.getId());

        List<ProviderEntity> providersAfterDelete = repository.findAll();
        if(providersAfterDelete.size() >= providersBeforeDelete.size()) {
            throw new IllegalStateException("There can not be more elements in the database after deletion than before deletion");
        }
        Assertions.assertTrue(repository.findById(providerToDelete.getId()).isEmpty());
    }

    @Test
    void httpDeleteReturnsDeletedResource() {
        ProviderEntity providerToDelete = repository.findAll().get(0);
        ResponseEntity<ProviderEntity> deletedProvider = controller.deleteById(providerToDelete.getId());
        Assertions.assertEquals(providerToDelete, deletedProvider.getBody());
    }

    @Test
    void successfulDeletionRetunsStatusCode200() {
        ProviderEntity providerToDelete = repository.findAll().get(0);
        ResponseEntity<ProviderEntity> deletedProvider = controller.deleteById(providerToDelete.getId());
        Assertions.assertEquals(HttpStatus.OK, deletedProvider.getStatusCode());
    }

    @Test
    void deletingNonExistentProviderReturnsEmtpyBodyAndStatusCode204() {
        ResponseEntity<ProviderEntity> deletedProvider = controller.deleteById(1000);
        Assertions.assertNull(deletedProvider.getBody());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, deletedProvider.getStatusCode());
    }


}
