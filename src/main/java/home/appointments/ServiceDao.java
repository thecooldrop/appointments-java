package home.appointments;

import org.springframework.data.jpa.repository.JpaRepository;

interface ServiceDao extends JpaRepository<ServiceEntity, Integer> {

    ServiceEntity findByName(String name);
}
