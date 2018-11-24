package bme.swarch.travellagency.hotelservice.service;

import bme.swarch.travellagency.hotelservice.api.ReservationDTO;
import bme.swarch.travellagency.hotelservice.exception.BadRequestException;
import bme.swarch.travellagency.hotelservice.model.Reservation;
import bme.swarch.travellagency.hotelservice.repository.ReservationRepository;
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
    private ModelMapper modelMapper;

    public List<ReservationDTO> getAllReservationForRoom(Long roomId) {
        List<Reservation> reservations = repository.findAllByRoomId(roomId);
        return reservations.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public boolean isRoomFree(Long roomId, Date start, Date end) {
        List<Reservation> reservations = repository.findAllByRoomId(roomId);
        return isReservationAcceptable(start, end, reservations);
    }

    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Reservation reservation = convertToEntity(reservationDTO);
        List<Reservation> oldReservations = repository.findAllByRoomId(reservationDTO.getRoomId());
        if (isReservationAcceptable(reservation.getStart(),reservation.getEnd(), oldReservations)) {
            Reservation savedReservation = repository.save(reservation);
            return convertToDTO(savedReservation);
        } else {
            throw new BadRequestException("Invalid request parameters. The resource already reserved!");
        }
    }

    //TODO into util
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

    //TODO into util as generic
    private ReservationDTO convertToDTO(Reservation reservation) {
        return modelMapper.map(reservation, ReservationDTO.class);
    }

    //TODO into util as generic
    private Reservation convertToEntity(ReservationDTO reservationDTO) {
        return modelMapper.map(reservationDTO, Reservation.class);
    }
}
