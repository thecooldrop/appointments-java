package home.appointments.location;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootTest
@Transactional
public class LocationControllerHttpGetTest {

    @Autowired
    private LocationsController locationsController;

    @Autowired
    private LocationsRepository locationsRepository;

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
        Assertions.assertThrows(LocationDoesNotExistException.class, () -> locationsController.getById(100L));
    }

    @Test
    void tryingToRetrieveALocationWhichDoesNotExistResultsIn404HttpStatusCode() {
        try {
            locationsController.getById(100L);
        } catch (LocationDoesNotExistException e) {
            Assertions.assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        }
    }
}
