package home.appointments.location;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

@SpringBootTest
@Transactional
public class LocationEntityPersistenceTest {

    @PersistenceContext
    private EntityManager em;

    @Test
    void canNotInsertLocationWithSameNameTwice() {
        LocationEntity first = new LocationEntity("first");
        LocationEntity second = new LocationEntity("first");
        Assertions.assertThrows(PersistenceException.class, () -> {
            em.persist(first);
            em.persist(second);
            em.flush();
        });
    }
}
