package bme.swarch.travellagency.carservice.service;

import bme.swarch.travellagency.carservice.api.CarDTO;
import bme.swarch.travellagency.carservice.api.CarSearchRequest;
import bme.swarch.travellagency.carservice.exception.ResourceNotFoundException;
import bme.swarch.travellagency.carservice.model.Car;
import bme.swarch.travellagency.carservice.repository.CarRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarService {
    private static final Logger logger = LoggerFactory.getLogger(CarService.class);

    @Autowired
    private CarRepository repository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ModelMapper modelMapper;

    public List<CarDTO> getAllCars() {
        List<Car> cars = repository.findAll();
        return cars.stream()
                .map(c -> convertToDto(c))
                .collect(Collectors.toList());
    }

    public CarDTO createCar(CarDTO carDto) {
        logger.debug("Create new car: {}", carDto);
        Car car = convertToEntity(carDto);
        Car createdCar = repository.save(car);
        return convertToDto(createdCar);
    }

    public CarDTO getCarById(Long id) {
        Car car = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));
        return convertToDto(car);
    }

    public void deleteCar(Long id) {
        Car storedCar = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found with id: " + id));

        repository.delete(storedCar);
    }

    public List<CarDTO> getAllCarFromPlace(String country, String city) {
        List<Car> cars = repository.findAllByCountryAndCity(country,city);
        return cars.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<CarDTO> getFreeCars(CarSearchRequest carSearchRequest) {
        List<Car> cars = repository.findAllByCountryAndCity(carSearchRequest.getCountry(), carSearchRequest.getCity());
        List<Car> freeCars = cars.stream()
                .filter(c -> reservationService.isCarFree(c.getId(), carSearchRequest.getStart(), carSearchRequest.getEnd()))
                .collect(Collectors.toList());
        return freeCars.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private CarDTO convertToDto(Car car) {
        CarDTO carDTO = modelMapper.map(car, CarDTO.class);
        return carDTO;
    }

    private Car convertToEntity(CarDTO carDTO) {
        Car car = modelMapper.map(carDTO, Car.class);
        return car;
    }
}
