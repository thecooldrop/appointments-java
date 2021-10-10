package home.appointments.service;

import org.springframework.data.jpa.repository.JpaRepository;

interface ServiceDao extends JpaRepository<ServiceEntity, Integer> {
}
