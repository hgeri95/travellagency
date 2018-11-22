package bme.swarch.travellagency.carservice.controller;

import bme.swarch.travellagency.carservice.api.CarDTO;
import bme.swarch.travellagency.carservice.api.CarSearchRequest;
import bme.swarch.travellagency.carservice.api.ReservationDTO;
import bme.swarch.travellagency.carservice.service.CarService;
import bme.swarch.travellagency.carservice.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/car/all")
    public List<CarDTO> getAllCars() {
        return carService.getAllCars();
    }

    @PostMapping("/car")
    public CarDTO createCar(@Valid @RequestBody CarDTO carDto) {
        return carService.createCar(carDto);
    }

    @GetMapping("/car/{id}")
    public CarDTO getCar(@PathVariable(value = "id") Long id) {
        return carService.getCarById(id);
    }

    @DeleteMapping("/car/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable("id") Long id) {
        carService.deleteCar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/car/{country}/{city}")
    public List<CarDTO> getCarFromPlace(@PathVariable("country") String country, @PathVariable("city") String city) {
        //TODO
        return Collections.emptyList();
    }

    @PostMapping("/car/reservation")
    public ReservationDTO createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        //TODO
        return null;
    }

    @GetMapping("/car/reservation/{id}")
    public List<ReservationDTO> getAllReservationForCar(@PathVariable("id") Long id) {
        //TODO
        return Collections.emptyList();
    }

    @GetMapping("/car/free")
    public List<CarDTO> getFreeCars(@Valid @RequestBody CarSearchRequest request) {
        //TODO
        return Collections.emptyList();
    }
}
