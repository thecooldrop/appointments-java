package home.appointments.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class LocationsController {

    @Autowired
    private LocationsRepository repository;

    @GetMapping(path = "/locations")
    public ResponseEntity<List<LocationEntity>> get() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/locations/{id}")
    public ResponseEntity<LocationEntity> getById(@PathVariable("id") Long id) {
        return repository.findById(id)
                .map(e -> new ResponseEntity<>(e, HttpStatus.OK))
                .orElseThrow(LocationDoesNotExistException::new);
    }

    @PostMapping(path = "/locations")
    public ResponseEntity<LocationEntity> post(@RequestBody LocationName name) {
        return LocationEntity.from(name.getName())
                .map(repository::save)
                .map(e -> new ResponseEntity<>(e, HttpStatus.CREATED))
                .orElseThrow(InvalidLocationNameException::new);
    }

    @DeleteMapping(path = "/locations/{id}")
    public ResponseEntity<LocationEntity> deleteById(@PathVariable("id") Long id) {
        return repository.findById(id)
                .map(e -> {
                    repository.deleteById(e.getId());
                    return e;
                })
                .map(e -> new ResponseEntity<LocationEntity>(e, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NO_CONTENT));
    }
}
