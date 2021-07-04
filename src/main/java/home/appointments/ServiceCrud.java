package home.appointments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ServiceCrud {

    @Autowired
    private ServiceDao serviceDao;

    public Set<ServiceEntity> read() {
        return new HashSet<>(serviceDao.findAll());
    }

    public Optional<ServiceEntity> readById(Integer id) {
        return serviceDao.findById(id);
    }
}
