package home.appointments.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional(isolation = Isolation.SERIALIZABLE)
public class ServiceControllerTest {

    @Autowired
    private ServiceController controller;

    @Autowired
    private ServiceDao serviceDao;

    @Test
    public void canReturnAllServices() {
        List<ServiceEntity> services = populateDatabaseWithServiceInstances();
        ResponseEntity<Set<ServiceEntity>> restServices = controller.getAllServices();
        assertEquals(new HashSet<>(services), restServices.getBody());
    }

    @Test
    public void canReturnServiceById() {
        List<ServiceEntity> services = populateDatabaseWithServiceInstances();
        Integer serviceId = services.get(0).getId();
        ResponseEntity<ServiceEntity> serviceResponse = controller.getServiceById(serviceId);
        ServiceEntity serviceInstance = serviceResponse.getBody();
        assertEquals(services.get(0), serviceInstance);
    }

    @Test
    public void returnsExceptionWhenAskingForServiceByIdWhenNoServiceHasId() {
        populateDatabaseWithServiceInstances();
        assertThrows(ServiceNotFoundException.class, () -> controller.getServiceById(999));
    }

    @Test
    public void returnsStatus404WhenAskingForServiceByIdWhenNoServiceHasSuchId() {
        populateDatabaseWithServiceInstances();
        try {
            controller.getServiceById(999);
        } catch (ServiceNotFoundException ex) {
            assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
        }
    }

    private List<ServiceEntity> populateDatabaseWithServiceInstances() {
        ServiceEntity firstServiceEntity = new ServiceEntity("first");
        ServiceEntity secondServiceEntity = new ServiceEntity("second");
        ServiceEntity thirdServiceEntity = new ServiceEntity("third");

        List<ServiceEntity> serviceEntities = new ArrayList<>(3);
        serviceEntities.add(firstServiceEntity);
        serviceEntities.add(secondServiceEntity);
        serviceEntities.add(thirdServiceEntity);

        return serviceDao.saveAll(serviceEntities);
    }
}
