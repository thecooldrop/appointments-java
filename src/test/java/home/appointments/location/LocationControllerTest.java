package home.appointments.location;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
public class LocationControllerTest {

    @Autowired
    private LocationsController locationsController;

    @Autowired
    private LocationsRepository locationsRepository;

    private void prepareLocationEntitiesIntoDatabase() {
        LocationEntity first = new LocationEntity("first");
        LocationEntity second = new LocationEntity("second");
        LocationEntity third = new LocationEntity("third");
        List<LocationEntity> locations = Arrays.asList(first, second, third);
        locationsRepository.saveAll(locations);
    }

    @Test
    void getMethodRetrievesAllLocations() {
        prepareLocationEntitiesIntoDatabase();
        ResponseEntity<List<LocationEntity>> locations = locationsController.get();
        List<LocationEntity> locationsContent = locations.getBody();
        Assertions.assertEquals(locationsContent.size(), 3);
        Assertions.assertEquals("first", locationsContent.get(0).getName());
        Assertions.assertEquals("second", locationsContent.get(1).getName());
        Assertions.assertEquals("third", locationsContent.get(2).getName());
    }

    @Test
    void returnsOkStatusCodeOnSuccess() {
        prepareLocationEntitiesIntoDatabase();
        Assertions.assertEquals(200, locationsController.get().getStatusCode().value());
    }
}
