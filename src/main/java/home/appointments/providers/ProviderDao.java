package home.appointments.providers;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderDao extends JpaRepository<ProviderEntity, Integer> {
}
