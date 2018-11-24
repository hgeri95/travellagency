package bme.swarch.travellagency.agencyservice.repository;

import bme.swarch.travellagency.agencyservice.model.Journey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JourneyRepository extends JpaRepository<Journey, Long> {

    public List<Journey> findAllByFromCityAndToCity(String fromCity, String toCity);
}
