package home.appointments;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ServiceControllerTest {

    @Autowired
    private ServiceController controller;

    @Autowired
    private ServiceDao serviceDao;

    @Test
    public void canReturnAllServices() {
        Set<ServiceEntity> services = populateDatabaseWithServiceInstances();
        ResponseEntity<Set<ServiceEntity>> restServices = controller.getAllServices();
        assertEquals(services, restServices.getBody());
    }

    @Test
    public void canReturnServiceById() {
        populateDatabaseWithServiceInstances();
        ResponseEntity<ServiceEntity> serviceResponse = controller.getServiceById(1);
        ServiceEntity serviceInstance = serviceResponse.getBody();
        assertEquals(1, serviceInstance.getId());
        assertEquals("first", serviceInstance.getName());
    }

    @Test
    public void returns404WhenAskingForServiceByIdWhenNoServiceHasId() {
        populateDatabaseWithServiceInstances();
        ResponseEntity<ServiceEntity> serviceResponse = controller.getServiceById(999);
        assertEquals(HttpStatus.NOT_FOUND, serviceResponse.getStatusCode());
    }

    private Set<ServiceEntity> populateDatabaseWithServiceInstances() {
        ServiceEntity firstServiceEntity = new ServiceEntity("first");
        ServiceEntity secondServiceEntity = new ServiceEntity("second");
        ServiceEntity thirdServiceEntity = new ServiceEntity("third");

        Set<ServiceEntity> serviceEntities = new HashSet<>(3);
        serviceEntities.add(firstServiceEntity);
        serviceEntities.add(secondServiceEntity);
        serviceEntities.add(thirdServiceEntity);

        return new HashSet<>(serviceDao.saveAll(serviceEntities));
    }
}
