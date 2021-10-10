package home.appointments.service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceDao extends JpaRepository<PriceEntity, Integer> {
}
