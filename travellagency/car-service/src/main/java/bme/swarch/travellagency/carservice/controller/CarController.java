package bme.swarch.travellagency.carservice.controller;

import bme.swarch.travellagency.carservice.model.Car;
import bme.swarch.travellagency.carservice.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/car/all")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @PostMapping("/car")
    public Car createCar(@Valid @RequestBody Car car) {
        return carService.createCar(car);
    }

    @GetMapping("/car/{id}")
    public Car getCar(@PathVariable(value = "id") Long id) {
        return carService.getCarById(id);
    }

    @PutMapping("/car/{id}")
    public Car updateCar(@PathVariable("id") Long id, @Valid @RequestBody Car car) {
        return carService.updateCar(id, car);
    }

    @DeleteMapping("/car/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable("id") Long id) {
        carService.deleteCar(id);
        return ResponseEntity.ok().build();
    }
}
