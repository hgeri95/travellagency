package bme.swarch.travellagency.carservice.service;

import bme.swarch.travellagency.carservice.ResourceNotFoundException;
import bme.swarch.travellagency.carservice.model.Car;
import bme.swarch.travellagency.carservice.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    @Autowired
    CarRepository repository;

    public List<Car> getAllCars() {
        return repository.findAll();
    }

    public Car createCar(Car car) {
        logger.debug("Create new car: {}", car);
        return repository.save(car);
    }

    public Car getCarById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
    }

    public Car updateCar(Long id, Car car) {
        Car storedCar = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));

        storedCar.setType(car.getType());
        storedCar.setBootSize(car.getBootSize());
        storedCar.setFree(car.isFree());
        storedCar.setSeats(car.getSeats());

        Car updatedCar = repository.save(storedCar);
        return updatedCar;
    }

    public void deleteCar(Long id) {
        Car storedCar = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));

        repository.delete(storedCar);
    }
}
