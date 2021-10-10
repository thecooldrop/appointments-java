package home.appointments.service;

import org.springframework.data.jpa.repository.JpaRepository;

interface PriceDao extends JpaRepository<PriceEntity, Integer> {
}
