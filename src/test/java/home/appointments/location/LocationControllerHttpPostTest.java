package home.appointments.location;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
public class LocationControllerHttpPostTest {

    @Autowired
    private LocationsController locationsController;

    @Autowired
    private LocationsRepository locationsRepository;

    @Test
    void canCreateNewLocationEntityWithCorrectInput() {
        locationsController.post(new LocationName("location"));
        List<LocationEntity> locationsInDatabase = locationsRepository.findAll();

        Assertions.assertEquals(1, locationsInDatabase.size());

        LocationEntity location = locationsInDatabase.get(0);
        Assertions.assertEquals("location", location.getName());
        Assertions.assertNotNull(location.getId());
    }

    @Test
    void whenNewLocationEntityIsSuccessfullyCreatedReturnHttpStatusCode201() {
        Assertions.assertEquals(HttpStatus.CREATED, locationsController.post(new LocationName("location")).getStatusCode());
    }

    @Test
    void whenImproperlyFormattedLocationNameIsGivenReturnsHttpStatusCode400() {
        String[] improperlyFormattedLocationNames = new String[] {null, "", "\n\t", "a".repeat(256)};
        for(String improperlyFormattedLocationName : improperlyFormattedLocationNames) {
            try {
                locationsController.post(new LocationName(improperlyFormattedLocationName));
            } catch (InvalidLocationNameException e) {
                Assertions.assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
            }
        }
    }

    @Test
    void whenImproperlyFormattedLocationNameIsGivenThrowsInvalidLocationNameException() {
        String[] improperlyFormattedLocationNames = new String[] {null, "", "\n\t", "a".repeat(256)};
        for(String improperlyFormattedLocationName : improperlyFormattedLocationNames) {
            Assertions.assertThrows(InvalidLocationNameException.class, () -> locationsController.post(new LocationName(improperlyFormattedLocationName)));
        }
    }

}
