package home.appointments.service;

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
public class ServiceControllerDeleteTest {

    @Autowired
    private ServiceController serviceController;

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private PriceDao priceDao;

    @BeforeEach
    void saveServicesInDatabase() {
        PriceEntity firstServicePrice = new PriceEntity(1,0);
        PriceEntity secondServicePrice = new PriceEntity(10, 0);
        PriceEntity thirdServicePrice = new PriceEntity(100, 0);
        ServiceEntity firstTestService = new ServiceEntity("first service", "First service description", 1, firstServicePrice);
        ServiceEntity secondTestService = new ServiceEntity("second service", "Second service description", 10, secondServicePrice);
        ServiceEntity thirdTestService = new ServiceEntity("third service", "Third service description", 100, thirdServicePrice);
        priceDao.saveAll(Arrays.asList(firstServicePrice, secondServicePrice, thirdServicePrice));
        serviceDao.saveAll(Arrays.asList(firstTestService, secondTestService, thirdTestService));
        priceDao.flush();
        serviceDao.flush();
    }

    @Test
    void deleteRemovesTheServiceAndCorrespondingPriceFromDatabase() {
        List<ServiceEntity> servicesInDb = serviceDao.findAll();
        ServiceEntity serviceToDelete = servicesInDb.get(0);

        serviceController.deleteById(serviceToDelete.getId());

        List<ServiceEntity> servicesAfterDelete = serviceDao.findAll();
        Assertions.assertFalse(servicesAfterDelete.contains(serviceToDelete));
        Assertions.assertTrue(servicesAfterDelete.size() < servicesInDb.size());
        Assertions.assertFalse(priceDao.findAll().contains(serviceToDelete.getServicePrice()));
    }

    @Test
    void deleteReturnsTheDeletedResource() {
        List<ServiceEntity> servicesInDb = serviceDao.findAll();
        ServiceEntity serviceToDelete = servicesInDb.get(0);
        ResponseEntity<ServiceEntity> response = serviceController.deleteById(serviceToDelete.getId());
        Assertions.assertEquals(serviceToDelete, response.getBody());
    }

    @Test
    void successfulDeletionReturnsStatusCode200() {
        List<ServiceEntity> servicesInDb = serviceDao.findAll();
        ServiceEntity serviceToDelete = servicesInDb.get(0);
        ResponseEntity<ServiceEntity> response = serviceController.deleteById(serviceToDelete.getId());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deletingNonExistentResourceReturnsEmptyBodyAndHttp204() {
        ResponseEntity<ServiceEntity> response = serviceController.deleteById(1000);
        Assertions.assertNull(response.getBody());
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}
