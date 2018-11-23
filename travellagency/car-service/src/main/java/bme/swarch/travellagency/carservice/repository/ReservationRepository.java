package bme.swarch.travellagency.carservice.repository;

import bme.swarch.travellagency.carservice.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    public List<Reservation> findAllByCarId(Long carId);
}
