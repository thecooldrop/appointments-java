package home.appointments.location;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootTest
@Transactional
public class LocationControllerTest {

    @Autowired
    private LocationsController locationsController;

    @Autowired
    private LocationsRepository locationsRepository;

    @BeforeEach
     void prepareLocationEntitiesIntoDatabase() {
        LocationEntity first = new LocationEntity("first");
        LocationEntity second = new LocationEntity("second");
        LocationEntity third = new LocationEntity("third");
        List<LocationEntity> locations = Arrays.asList(first, second, third);
        locationsRepository.saveAll(locations);
        locationsRepository.flush();
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

    @Test
    void getMethodRetrievesSpecificLocation() {
        List<LocationEntity> locationsInDatabase = locationsRepository.findAll();
        for(LocationEntity entity : locationsInDatabase) {
            Assertions.assertEquals(entity, locationsController.getById(entity.getId()).getBody());
        }
    }

    @Test
    void successfullyRetrivingSpecificLocationResultsIn200HttpCode() {
        List<LocationEntity> locationsInDatabase = locationsRepository.findAll();
        for(LocationEntity entity : locationsInDatabase) {
            Assertions.assertEquals(HttpStatus.OK, locationsController.getById(entity.getId()).getStatusCode());
        }
    }

    @Test
    void tryingToRetrieveALocationWhichDoesNotExistResultsInLocationDoesNotExistException() {
        Long nonExistentId = Collections.max(locationEntityIds()) + 100;
        Assertions.assertThrows(LocationDoesNotExistException.class, () -> locationsController.getById(nonExistentId));
    }

    @Test
    void tryingToRetrieveALocationWhichDoesNotExistResultsIn404HttpStatusCode() {
        Long nonExistentId = Collections.max(locationEntityIds()) + 100;
        try {
            locationsController.getById(nonExistentId);
        } catch (LocationDoesNotExistException e) {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }

    private List<Long> locationEntityIds() {
        List<LocationEntity> locationsInDatabase = locationsRepository.findAll();
        List<Long> locationEntityIds = new ArrayList<>();
        for(LocationEntity entity : locationsInDatabase) {
            locationEntityIds.add(entity.getId());
        }
        return locationEntityIds;
    }
}
