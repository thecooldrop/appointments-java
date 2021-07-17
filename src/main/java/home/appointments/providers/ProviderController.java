package home.appointments.providers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
class ProviderController {

    private final ProviderDao providerAccessor;

    @Autowired
    ProviderController(ProviderDao providerAccessor) {
        this.providerAccessor = providerAccessor;
    }

    @GetMapping(path = "/providers", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<ProviderEntity>> getProviders() {
        return ResponseEntity.ok(new HashSet<>(providerAccessor.findAll()));
    }

    @GetMapping(path = "/prvoiders/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProviderEntity> getProviderById(@PathVariable Integer id) {
        return providerAccessor.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(ProviderNotFoundException::new);
    }
}