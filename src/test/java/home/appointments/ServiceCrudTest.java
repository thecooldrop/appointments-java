package home.appointments;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ServiceCrudTest {

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private ServiceCrud serviceCrudService;

    @Test
    public void canRetrieveAllServiceInstancesFromDatabase() {
        Set<ServiceEntity> servicesPopulated = populateDatabaseWithServiceInstances();
        Set<ServiceEntity> servicesReadIn = serviceCrudService.read();
        assertEquals(servicesPopulated, servicesReadIn);
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
