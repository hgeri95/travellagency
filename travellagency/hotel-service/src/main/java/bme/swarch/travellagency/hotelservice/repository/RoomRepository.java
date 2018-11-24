package bme.swarch.travellagency.hotelservice.repository;

import bme.swarch.travellagency.hotelservice.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    public List<Room> findAllByCity(String city);
}
