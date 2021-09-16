package home.appointments.location;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
@Transactional
public class LocationControllerHttpDeleteTest {

    @Autowired
    private LocationsController locationsController;

    @Autowired
    private LocationsRepository locationsRepository;

    @BeforeEach
    void instantiateLocations() {
        LocationEntity firstLocation = new LocationEntity("first");
        LocationEntity secondLocation = new LocationEntity("second");
        LocationEntity thirdLocation = new LocationEntity("third");
        locationsRepository.saveAll(Arrays.asList(firstLocation, secondLocation, thirdLocation));
    }

    @Test
    void httpDeleteRemovesTheLocationFromDatabase() {
        List<LocationEntity> locationsBeforeDelete = locationsRepository.findAll();
        int numberOfLocationsBeforeDelete = locationsBeforeDelete.size();

        locationsController.deleteById(locationsBeforeDelete.get(0).getId());

        List<LocationEntity> locationsAfterDelete = locationsRepository.findAll();
        int numberOfLocationsAfterDelete = locationsAfterDelete.size();

        if(numberOfLocationsAfterDelete >= numberOfLocationsBeforeDelete) {
            throw new IllegalStateException("There should be one less entry after delete");
        }
    }

    @Test
    void httpDeleteReturnsDeletedResource() {
        LocationEntity locationToDelete = locationsRepository.findAll().get(0);
        var deletedLocation = locationsController.deleteById(locationToDelete.getId());
        Assertions.assertEquals(locationToDelete, deletedLocation.getBody());
    }

    @Test
    void successfullHttpDeleteReturnsStatusCode200() {
        LocationEntity locationToDelete = locationsRepository.findAll().get(0);
        HttpStatus statusCode = locationsController.deleteById(locationToDelete.getId()).getStatusCode();
        Assertions.assertEquals(HttpStatus.OK, statusCode);
    }

    @Test
    void deletingNonExistentResourceReturnsEmptyBody() {
        Long maxId = locationsRepository.findAll()
                .stream()
                .max(Comparator.comparingLong(LocationEntity::getId))
                .get()
                .getId();

        Long nonExistentId = maxId + 100;
        var deletedLocation = locationsController.deleteById(nonExistentId);
        Assertions.assertNull(deletedLocation.getBody());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, deletedLocation.getStatusCode());
    }

}
