package bme.swarch.travellagency.agencyservice.service;

import bme.swarch.travellagency.agencyservice.api.DetailedReservationDTO;
import bme.swarch.travellagency.agencyservice.api.JourneyDTO;
import bme.swarch.travellagency.agencyservice.api.ReservationRequestDTO;
import bme.swarch.travellagency.agencyservice.api.ReservationResponseDTO;
import bme.swarch.travellagency.agencyservice.exception.BadRequestException;
import bme.swarch.travellagency.agencyservice.exception.ResourceNotFoundException;
import bme.swarch.travellagency.agencyservice.model.Reservation;
import bme.swarch.travellagency.agencyservice.repository.ReservationRepository;
import bme.swarch.travellagency.carservice.api.ReservationDTO;
import bme.swarch.travellagency.planeservice.api.TicketDTO;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    private ReservationRepository repository;

    @Autowired
    private JourneyService journeyService;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${carservice.host:localhost}")
    private String carHost;

    @Value("${carservice.port:8091}")
    private int carPort;

    @Value("${hotelservice.host:localhost}")
    private String hotelHost;

    @Value("${hotelservice.post:8092}")
    private int hotelPort;

    @Value("${planeservice.host:localhost}")
    private String planeHost;

    @Value("${planeservice.port:8093}")
    private int planePort;

    private RestTemplate restTemplate = new RestTemplate();

    public List<ReservationResponseDTO> getAll() {
        List<Reservation> reservations = repository.findAll();
        return reservations.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public DetailedReservationDTO getDetailedReservation(Long id) {
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with id: " + id));

        DetailedReservationDTO detailedReservationDTO = new DetailedReservationDTO();
        detailedReservationDTO.setId(reservation.getId());
        detailedReservationDTO.setIdNumber(reservation.getIdNumber());
        detailedReservationDTO.setName(reservation.getName());
        detailedReservationDTO.setNumOfPersons(reservation.getNumOfPersons());

        JourneyDTO journeyDTO = journeyService.getById(reservation.getJourneyId());
        detailedReservationDTO.setJourney(journeyDTO);

        if (reservation.getCarReservationId() != null) {
            try {
                ReservationDTO carReservation =
                        restTemplate.getForObject(buildUrl(carHost, carPort, "/api/car/reservation/one/" + reservation.getCarReservationId()),
                                ReservationDTO.class);
                detailedReservationDTO.setCarReservation(carReservation);
            } catch (HttpClientErrorException ex) {
                logger.error("Http exception: {}", ex);
            }
        }

        if (reservation.getRoomReservationId() != null) {
            try {
                bme.swarch.travellagency.hotelservice.api.ReservationDTO roomReservation =
                        restTemplate.getForObject(buildUrl(hotelHost, hotelPort, "/api/room/reservation/one/" + reservation.getRoomReservationId()),
                                bme.swarch.travellagency.hotelservice.api.ReservationDTO.class);
                detailedReservationDTO.setRoomReservation(roomReservation);
            } catch (HttpClientErrorException ex) {
                logger.error("Http exception: {}", ex);
            }
        }

        if (reservation.getTicketReservationIds() != null) {
            List<TicketDTO> tickets = new ArrayList<>();
            for (Long ticketReservationId : reservation.getTicketReservationIds()) {
                try {
                    TicketDTO ticketDTO = restTemplate.getForObject(buildUrl(planeHost, planePort, "/api/ticket/" + ticketReservationId), TicketDTO.class);
                    tickets.add(ticketDTO);
                } catch (HttpClientErrorException ex) {
                    logger.error("Http exception: {}", ex);
                }
            }
            detailedReservationDTO.setTickets(tickets);
        }

        return detailedReservationDTO;
    }

    public DetailedReservationDTO createReservation(ReservationRequestDTO request) {
        JourneyDTO journeyDTO = journeyService.getById(request.getJourneyId());
        if (journeyDTO == null) {
            throw new ResourceNotFoundException("Journey not found with id: " + request.getJourneyId());
        }

        DetailedReservationDTO detailedReservationDTO = new DetailedReservationDTO();

        ReservationDTO carReservation = null;
        if (request.getCarId() != null) {
            ReservationDTO carReservationRequest = new ReservationDTO(request.getCarId(), journeyDTO.getStart(), journeyDTO.getEnd());
            try {
                carReservation = restTemplate
                        .postForObject(buildUrl(carHost, carPort, "/api/car/reservation"), carReservationRequest, ReservationDTO.class);
                checkReservation(carReservation);
                detailedReservationDTO.setCarReservation(carReservation);
            } catch (HttpClientErrorException ex) {
                logger.error("Http exception: {}", ex);
            }
        }

        bme.swarch.travellagency.hotelservice.api.ReservationDTO roomReservation = null;
        if (request.getRoomId() != null) {
            bme.swarch.travellagency.hotelservice.api.ReservationDTO roomReservationRequest =
                    new bme.swarch.travellagency.hotelservice.api.ReservationDTO(request.getRoomId(), journeyDTO.getStart(), journeyDTO.getEnd());
            try {
                roomReservation =
                        restTemplate.postForObject(buildUrl(hotelHost, hotelPort, "/api/room/reservation"), roomReservationRequest, bme.swarch.travellagency.hotelservice.api.ReservationDTO.class);
                checkReservation(roomReservation);
                detailedReservationDTO.setRoomReservation(roomReservation);
            } catch (HttpClientErrorException ex) {
                logger.error("Http exception: {}", ex);
            }
        }

        if (request.getTicketIds() != null) {
            List<TicketDTO> tickets = new ArrayList<>();
            for (Long ticketId : request.getTicketIds()) {
                try {
                    TicketDTO ticketDTO = restTemplate.postForObject(buildUrl(planeHost, planePort, "/api/ticket/reservation/" + ticketId), null, TicketDTO.class);
                    checkReservation(ticketDTO);
                    tickets.add(ticketDTO);
                } catch (HttpClientErrorException ex) {
                    logger.error("Http exception: {}", ex);
                }
            }
            detailedReservationDTO.setTickets(tickets);
        }

        final List<Long> ticketIds;
        if (detailedReservationDTO.getTickets() != null) {
            ticketIds = detailedReservationDTO.getTickets().stream().map(TicketDTO::getId).collect(Collectors.toList());
        } else {
            ticketIds = Collections.emptyList();
        }

        Reservation reservation = new Reservation(request.getJourneyId(), request.getNumOfPersons(), request.getName(),
                request.getIdNumber(),
                carReservation != null ? carReservation.getId() : null,
                roomReservation != null ? roomReservation.getId() : null,
                ticketIds);
        Reservation savedReservation = repository.save(reservation);

        if (savedReservation == null) {
            throw new BadRequestException("Failed to reserve journey!");
        } else {
            detailedReservationDTO.setJourney(journeyDTO);
            detailedReservationDTO.setNumOfPersons(savedReservation.getNumOfPersons());
            detailedReservationDTO.setName(savedReservation.getName());
            detailedReservationDTO.setIdNumber(savedReservation.getIdNumber());
            detailedReservationDTO.setId(savedReservation.getId());

            return detailedReservationDTO;
        }
    }

    public String buildUrl(String host, int port, String path) {
        return "http://" + host + ":" + port + path;
    }

    private void checkReservation(Object response) {
        if (response == null) {
            throw new BadRequestException("Reservation failed!");
        }
    }

    private ReservationResponseDTO convertToDTO(Reservation reservation) {
        return modelMapper.map(reservation, ReservationResponseDTO.class);
    }


}
