package home.appointments.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Provider;
import java.util.HashSet;
import java.util.Set;

@RestController
public class ServiceController {

    @Autowired
    private ServiceDao serviceData;

    @Autowired
    private PriceDao priceDao;

    @GetMapping(path = "/services", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<ServiceEntity>> getAllServices() {
        return ResponseEntity.ok(new HashSet<>(serviceData.findAll()));
    }

    @GetMapping(path = "/services/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceEntity> getServiceById(@PathVariable Integer id) {
        return serviceData.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(ServiceNotFoundException::new);
    }

    @PostMapping(path = "/services", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceEntity> post(@RequestBody ServiceRequest request) {
        PriceEntity savedPrice = priceDao.save(new PriceEntity(
                request.getPrice().getPriceKm(),
                request.getPrice().getPricePfening()
        ));
        ServiceEntity saved = serviceData.save(new ServiceEntity(request.getName(),
                request.getDescription(),
                request.getDuration(),
                savedPrice));
        return ResponseEntity.created(ServletUriComponentsBuilder.fromPath("/services/{id}").buildAndExpand(saved.getId()).toUri()).body(saved);
    }
}
