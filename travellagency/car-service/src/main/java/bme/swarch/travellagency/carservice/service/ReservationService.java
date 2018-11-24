package bme.swarch.travellagency.carservice.service;

import bme.swarch.travellagency.carservice.api.ReservationDTO;
import bme.swarch.travellagency.carservice.exception.BadRequestException;
import bme.swarch.travellagency.carservice.exception.ResourceNotFoundException;
import bme.swarch.travellagency.carservice.model.Reservation;
import bme.swarch.travellagency.carservice.repository.ReservationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository repository;

    @Autowired
    private CarService carService;

    @Autowired
    private ModelMapper modelMapper;

    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        if (carService.getCarById(reservationDTO.getCarId())==null) {
            throw new ResourceNotFoundException("Car not found with id: " + reservationDTO.getCarId());
        }
        Reservation reservation = convertToEntity(reservationDTO);
        List<Reservation> oldReservations = repository.findAllByCarId(reservationDTO.getCarId());
        if (isReservationAcceptable(reservation.getStart(),reservation.getEnd(), oldReservations)) {
            Reservation savedReservation = repository.save(reservation);
            return concertToDTO(savedReservation);
        } else {
            throw new BadRequestException("Invalid request parameters. The resource already reserved!");
        }
    }

    public ReservationDTO getReservationById(Long id) {
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));
        return concertToDTO(reservation);
    }

    public List<ReservationDTO> getAllReservationForCar(Long carId) {
        List<Reservation> reservations = repository.findAllByCarId(carId);
        return reservations.stream().map(this::concertToDTO).collect(Collectors.toList());
    }

    public boolean isCarFree(Long carId, Date start, Date end) {
        List<Reservation> reservations = repository.findAllByCarId(carId);
        return isReservationAcceptable(start, end, reservations);
    }

    private boolean isReservationAcceptable(Date newStart, Date newEnd, List<Reservation> oldReservations) {
        for (Reservation reservation : oldReservations) {
            if (newStart.after(reservation.getStart()) && newStart.before(reservation.getEnd())) {
                return false;
            }
            if (newEnd.after(reservation.getStart()) && newEnd.before(reservation.getEnd())) {
                return false;
            }
            if (newStart.before(reservation.getStart()) && newEnd.after(reservation.getEnd())) {
                return false;
            }
        }
        return true;
    }

    private Reservation convertToEntity(ReservationDTO reservationDTO) {
        return modelMapper.map(reservationDTO, Reservation.class);
    }

    private ReservationDTO concertToDTO(Reservation reservation) {
        return modelMapper.map(reservation, ReservationDTO.class);
    }
}
