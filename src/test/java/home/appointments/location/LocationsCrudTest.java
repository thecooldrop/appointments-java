package home.appointments.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootTest
@Transactional
public class LocationsCrudTest {
    @Autowired
    private LocationsController locationsController;

    @PersistenceContext
    private EntityManager em;

    void whenLocationsAreInDatabaseThenTheyCanBeObtainedViaGetEndpoint() {
        LocationEntity firstLocation = new LocationEntity("slatina tuzla");
    }
}
