package home.appointments.providers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public interface GenericDaoController<T, Ex extends Supplier<ResponseStatusException>> {

    JpaRepository<T, Integer> getDao();
    Supplier<ResponseStatusException> getExceptionSupplier();

    @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<Set<T>> getResources() {
        return ResponseEntity.ok(new HashSet<>(getDao().findAll()));
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<T> getResource(@PathVariable Integer id) {
        return getDao().findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(getExceptionSupplier());
    }

}
