package home.appointments.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocationsController {

    @Autowired
    private LocationsRepository repository;

    @GetMapping(path = "/locations")
    public ResponseEntity<List<LocationEntity>> get() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }
}
