package home.appointments.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
class ProviderController {

    private final ProviderRepository providerAccessor;

    @Autowired
    ProviderController(ProviderRepository providerAccessor) {
        this.providerAccessor = providerAccessor;
    }

    @GetMapping(path = "/providers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProviderEntity>> getProviders() {
        return ResponseEntity.ok(providerAccessor.findAll());
    }

    @GetMapping(path = "/providers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProviderEntity> getProviderById(@PathVariable Integer id) {
        return providerAccessor.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(ProviderNotFoundException::new);
    }

    @PostMapping(path="/providers")
    public ResponseEntity<ProviderEntity> post(@RequestBody ProviderRequest providerToCreate) {
        return ProviderEntity.from(providerToCreate.getFirstName(), providerToCreate.getLastName())
                .map(providerAccessor::save)
                .map(e -> ResponseEntity.created(ServletUriComponentsBuilder.fromPath("/providers/{id}").buildAndExpand(e.getId()).toUri()).body(e))
                .orElseThrow(InvalidProviderNames::new);
    }

    @DeleteMapping(path="/providers/{id}")
    public ResponseEntity<ProviderEntity> deleteById(@PathVariable Integer id) {
        return providerAccessor.findById(id)
                .map(e -> {
                    providerAccessor.deleteById(e.getId());
                    return e;
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }
}
