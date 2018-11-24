package bme.swarch.travellagency.carservice.controller;

import bme.swarch.travellagency.carservice.api.CarDTO;
import bme.swarch.travellagency.carservice.api.CarSearchRequest;
import bme.swarch.travellagency.carservice.api.ReservationDTO;
import bme.swarch.travellagency.carservice.exception.BadRequestException;
import bme.swarch.travellagency.carservice.service.CarService;
import bme.swarch.travellagency.carservice.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
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

    @GetMapping("/car/place/{city}")
    public List<CarDTO> getAllCarFromPlace(@PathVariable("city") String city) {
        if (city != null) {
            return carService.getAllCarFromPlace(city);
        } else {
            throw new BadRequestException("Path variables /{city} cannot be null!");
        }
    }

    @PostMapping("/car/reservation")
    public ReservationDTO createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        if (reservationDTO.getEnd().before(reservationDTO.getStart())) {
            throw new BadRequestException("End date before start!");
        } else {
            return reservationService.createReservation(reservationDTO);
        }
    }

    @GetMapping("/car/reservation/one/{id}")
    public ReservationDTO getReservationById(@PathVariable("id") Long id) {
        return reservationService.getReservationById(id);
    }

    @GetMapping("/car/reservation/{id}")
    public List<ReservationDTO> getAllReservationForCar(@PathVariable("id") Long id) {
        if (id != null) {
            return reservationService.getAllReservationForCar(id);
        } else {
            throw new BadRequestException("Path variable carId cannot be null!");
        }
    }

    @PostMapping("/car/free")
    public List<CarDTO> getFreeCars(@Valid @RequestBody CarSearchRequest request) {
        return carService.getFreeCars(request);
    }
}
