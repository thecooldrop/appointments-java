package home.appointments;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class ServiceController {

    @Autowired
    private ServiceCrud serviceCrud;

    @GetMapping(path = "/services", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<ServiceEntity>> getAllServices() {
        return ResponseEntity.ok(serviceCrud.read());
    }

    @GetMapping(path = "/services/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceEntity> getServiceById(@PathVariable Integer id) {
        return serviceCrud.readById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
