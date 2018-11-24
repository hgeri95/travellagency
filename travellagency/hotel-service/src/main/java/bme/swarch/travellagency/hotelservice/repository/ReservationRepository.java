package bme.swarch.travellagency.hotelservice.repository;

import bme.swarch.travellagency.hotelservice.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    public List<Reservation> findAllByRoomId(Long roomId);
}
