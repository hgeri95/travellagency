package bme.swarch.travellagency.planeservice.repository;

import bme.swarch.travellagency.planeservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    public List<Ticket> findAllByFromCityAndToCity(String fromCity, String toCity);

}
