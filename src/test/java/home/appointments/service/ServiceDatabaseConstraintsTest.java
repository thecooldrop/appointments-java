package home.appointments.service;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.sql.SQLIntegrityConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional(isolation = Isolation.SERIALIZABLE)
public class ServiceDatabaseConstraintsTest {

    @Autowired
    private ServiceDao serviceDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testServiceNameIsUniqueInDatabase() {
        assertThrows(PersistenceException.class, () -> {
            ServiceEntity first = new ServiceEntity("first");
            ServiceEntity second = new ServiceEntity("first");
            serviceDao.save(first);
            serviceDao.save(second);
            em.flush();
        });
    }
}
