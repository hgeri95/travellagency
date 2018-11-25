package bme.swarch.travellagency.agencyservice.controller;

import bme.swarch.travellagency.agencyservice.api.*;
import bme.swarch.travellagency.agencyservice.exception.BadRequestException;
import bme.swarch.travellagency.agencyservice.service.JourneyService;
import bme.swarch.travellagency.agencyservice.service.ReservationService;
import bme.swarch.travellagency.agencyservice.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/agency")
public class AgencyController {

    @Autowired
    private UserService userService;

    @Autowired
    private JourneyService journeyService;

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/user")
    @ApiOperation("Create user")
    public UserDTO createUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @PostMapping("/user/login")
    @ApiOperation("Login with user")
    public boolean login(@Valid @RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @GetMapping("/journey/all")
    @ApiOperation("Get all journeys")
    public List<JourneyDTO> getAllJourneys() {
        return journeyService.getAll();
    }

    @PostMapping("/journey/search")
    @ApiOperation("Search for jouneys")
    public List<JourneyDTO> search(@Valid @RequestBody JourneySearchRequest request) {
        return journeyService.search(request);
    }

    @GetMapping("/journey/{id}")
    @ApiOperation("Get jouney by journey id")
    public DetailedJourneyDTO getJourneyById(@PathVariable("id") Long id) throws JsonProcessingException {
        return journeyService.getDetailedJourney(id);
    }

    @PostMapping("/journey")
    @ApiOperation("Create new journey")
    public JourneyDTO createJourney(@Valid @RequestBody JourneyDTO journeyDTO) {
        if (journeyDTO.getEnd().before(journeyDTO.getStart())) {
            throw new BadRequestException("End date cannot be before start date!");
        }
        return journeyService.create(journeyDTO);
    }

    @GetMapping("/reservation/all")
    @ApiOperation("Get all journey reservations")
    public List<ReservationResponseDTO> getAllReservations() {
        return reservationService.getAll();
    }

    @GetMapping("/reservation/{id}")
    @ApiOperation("Get detailedd reservatin by reservation id")
    public DetailedReservationDTO getReservationById(@PathVariable("id") Long id) {
        return reservationService.getDetailedReservation(id);
    }

    @PostMapping("/reservation")
    @ApiOperation("Register reservation")
    public DetailedReservationDTO createReservation(@Valid @RequestBody ReservationRequestDTO reservationRequestDTO) {
        return reservationService.createReservation(reservationRequestDTO);
    }
}

