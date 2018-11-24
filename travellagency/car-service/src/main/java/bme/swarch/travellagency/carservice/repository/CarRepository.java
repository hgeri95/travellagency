package bme.swarch.travellagency.carservice.repository;

import bme.swarch.travellagency.carservice.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    public List<Car> findAllByCity(String city);
}
