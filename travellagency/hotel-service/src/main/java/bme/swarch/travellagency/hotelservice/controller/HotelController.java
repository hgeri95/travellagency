package bme.swarch.travellagency.hotelservice.controller;

import bme.swarch.travellagency.hotelservice.api.ReservationDTO;
import bme.swarch.travellagency.hotelservice.api.RoomDTO;
import bme.swarch.travellagency.hotelservice.api.RoomSearchRequest;
import bme.swarch.travellagency.hotelservice.exception.BadRequestException;
import bme.swarch.travellagency.hotelservice.service.HotelService;
import bme.swarch.travellagency.hotelservice.service.ReservationService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/room")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/all")
    @ApiOperation("Get all rooms")
    public List<RoomDTO> getAllRooms() {
        return hotelService.getAllRooms();
    }

    @GetMapping("/{id}")
    @ApiOperation("Get room by room id")
    public RoomDTO getRoomById(@PathVariable("id") Long id) {
        return hotelService.getRoomById(id);
    }

    @GetMapping("/place/{city}")
    @ApiOperation("Get all ooms from a place")
    public List<RoomDTO> getAllRoomFromPlace(@PathVariable("city") String city) {
        if (city != null) {
            return hotelService.getAllRoomFromPlace(city);
        } else {
            throw new BadRequestException("Path parameters /{city} cannot be null!");
        }
    }

    @GetMapping("/reservation/{id}")
    @ApiOperation("Get all reservations by room id")
    public List<ReservationDTO> getAllReservationForRoom(@PathVariable("id") Long roomId) {
        return reservationService.getAllReservationForRoom(roomId);
    }

    @PostMapping("/free")
    @ApiOperation("Search for free rooms")
    public List<RoomDTO> searchFreeRoom(@Valid @RequestBody RoomSearchRequest request) {
        return hotelService.getFreeRooms(request);
    }

    @GetMapping("/reservation/one/{id}")
    @ApiOperation("Get reservation by reservation id")
    public ReservationDTO getReservationById(@PathVariable("id") Long id) {
        return reservationService.getReservationById(id);
    }

    @PostMapping("/reservation")
    @ApiOperation("Register reservation")
    public ReservationDTO createReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        if (reservationDTO.getEnd().before(reservationDTO.getStart())) {
            throw new BadRequestException("End date before start!");
        } else {
            return reservationService.createReservation(reservationDTO);
        }
    }
}
