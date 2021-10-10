package home.appointments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ServiceControllerDeleteTest {

    @Autowired
    private ServiceController serviceController;

    @Autowired
    private ServiceDao serviceDao;

    @Autowired
    private PriceDao priceDao;
}
