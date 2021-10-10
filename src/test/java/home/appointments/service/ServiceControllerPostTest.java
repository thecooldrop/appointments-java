package home.appointments.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.net.URI;
import java.sql.ResultSet;
import java.util.List;


@SpringBootTest
@Transactional
public class ServiceControllerPostTest {

    @Autowired
    private ServiceController controller;

    @Autowired
    private ServiceDao serviceDao;

    @PersistenceContext
    private EntityManager em;


    @Test
    void successfulCreationOfNewServiceSavesTheServiceAndPriceEntitiesIntoDatabase() {
        ServiceRequest request = new ServiceRequest("test service", "Service for testing", 90, new PriceRequest(100, 0));
        controller.post(request);

        List<ServiceEntity> servicesInDb = serviceDao.findAll();
        List<PriceEntity> pricesInDb = allPrices();

        Assertions.assertEquals(1, servicesInDb.size());
        Assertions.assertEquals(1, pricesInDb.size());

        ServiceEntity serviceInDb = servicesInDb.get(0);
        PriceEntity priceInDB = pricesInDb.get(0);
        Assertions.assertEquals("test service", serviceInDb.getName());
        Assertions.assertEquals("Service for testing", serviceInDb.getDescription());
        Assertions.assertEquals(90, serviceInDb.getDurationMinutes());
        Assertions.assertSame(priceInDB, serviceInDb.getServicePrice());
        Assertions.assertEquals(100, priceInDB.getPriceUnit());
        Assertions.assertEquals(0, priceInDB.getPriceHundredth());
    }

    @Test
    void sucessfulCreationOfNewServicesReturnsProperLocationHeader() {
        ServiceRequest request = new ServiceRequest("test service", "Service for testing", 90, new PriceRequest(100, 0));
        ResponseEntity<ServiceEntity> response = controller.post(request);
        ServiceEntity responseBody = response.getBody();
        URI locationHeader = response.getHeaders().getLocation();
        Assertions.assertEquals(URI.create("/services/" + responseBody.getId()), locationHeader);
    }

    private List<PriceEntity> allPrices() {
        CriteriaQuery<PriceEntity> priceCriteria = em.getCriteriaBuilder().createQuery(PriceEntity.class);
        return em.createQuery(priceCriteria.select(priceCriteria.from(PriceEntity.class))).getResultList();
    }
}
