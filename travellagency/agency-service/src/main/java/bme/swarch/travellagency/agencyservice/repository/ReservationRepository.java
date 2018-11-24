package bme.swarch.travellagency.agencyservice.repository;

import bme.swarch.travellagency.agencyservice.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
