package home.appointments;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceReader extends JpaRepository<Service, Integer> {
}
