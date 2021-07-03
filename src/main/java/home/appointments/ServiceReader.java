package home.appointments;

import org.springframework.data.jpa.repository.JpaRepository;

interface ServiceReader extends JpaRepository<Service, Integer> {
}
