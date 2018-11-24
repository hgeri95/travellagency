package bme.swarch.travellagency.agencyservice.service;

import bme.swarch.travellagency.agencyservice.api.DetailedJourneyDTO;
import bme.swarch.travellagency.agencyservice.api.JourneyDTO;
import bme.swarch.travellagency.agencyservice.api.JourneySearchRequest;
import bme.swarch.travellagency.agencyservice.exception.ResourceNotFoundException;
import bme.swarch.travellagency.agencyservice.model.Journey;
import bme.swarch.travellagency.agencyservice.repository.JourneyRepository;
import bme.swarch.travellagency.carservice.api.CarDTO;
import bme.swarch.travellagency.carservice.api.CarSearchRequest;
import bme.swarch.travellagency.hotelservice.api.RoomDTO;
import bme.swarch.travellagency.hotelservice.api.RoomSearchRequest;
import bme.swarch.travellagency.planeservice.api.TicketDTO;
import bme.swarch.travellagency.planeservice.api.TicketSearchRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JourneyService {

    @Autowired
    private JourneyRepository repository;

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

    private final RestTemplate restTemplate = new RestTemplate();

    private ObjectMapper objectMapper = new ObjectMapper();

    public List<JourneyDTO> getAll() {
        List<Journey> journeys = repository.findAll();
        return journeys.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public JourneyDTO getById(Long id) {
        Journey journey = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journey not found with id: " + id));
        return convertToDTO(journey);
    }

    public JourneyDTO create(JourneyDTO journeyDTO) {
        Journey journey = convertToEntity(journeyDTO);
        return convertToDTO(repository.save(journey));
    }

    public List<JourneyDTO> search(JourneySearchRequest searchRequest) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        List<Journey> journeys = repository.findAllByFromCityAndToCity(searchRequest.getFromCity(), searchRequest.getToCity());

        List<Journey> filteredByStart;
        if (searchRequest.getStart() != null) {
            String searchStart = dateFormat.format(searchRequest.getStart());
            filteredByStart = journeys.stream().filter(j -> searchStart.equals(dateFormat.format(j.getStart()))).collect(Collectors.toList());
        } else {
            filteredByStart = new ArrayList<>(journeys);
        }

        List<Journey> filteredByStartAndEnd;
        if (searchRequest.getEnd() != null) {
            String searchEnd = dateFormat.format(searchRequest.getEnd());
            filteredByStartAndEnd = filteredByStart.stream().filter(j -> searchEnd.equals(dateFormat.format(j.getEnd()))).collect(Collectors.toList());
        } else {
            filteredByStartAndEnd = new ArrayList<>(filteredByStart);
        }

        return filteredByStartAndEnd.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public DetailedJourneyDTO getDetailedJourney(Long id) throws JsonProcessingException {
        Journey journey = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journey not found with id: " + id));

        CarSearchRequest carSearchRequest = new CarSearchRequest(journey.getToCity(), journey.getStart(), journey.getEnd());
        List<CarDTO> cars = restTemplate.postForObject(buildUrl(carHost, carPort, "/api/car/free"), carSearchRequest, List.class);

        RoomSearchRequest roomSearchRequest = new RoomSearchRequest(journey.getToCity(), journey.getStart(), journey.getEnd());
        List<RoomDTO> rooms = restTemplate.postForObject(buildUrl(hotelHost, hotelPort, "/api/room/free"), roomSearchRequest, List.class);

        TicketSearchRequest ticketSearchRequest = new TicketSearchRequest(journey.getFromCity(), journey.getToCity(), journey.getStart());
        List<TicketDTO> tickets = restTemplate.postForObject(buildUrl(planeHost, planePort, "/api/ticket/free"), ticketSearchRequest, List.class);

        ticketSearchRequest = new TicketSearchRequest(journey.getToCity(), journey.getFromCity(), journey.getEnd());
        List<TicketDTO> backTickets = restTemplate.postForObject(buildUrl(planeHost, planePort, "/api/ticket/free"), ticketSearchRequest, List.class);
        if (tickets != null && backTickets != null) {
            tickets.addAll(backTickets);
        }

        DetailedJourneyDTO detailedJourneyDTO = new DetailedJourneyDTO();
        detailedJourneyDTO.setId(journey.getId());
        detailedJourneyDTO.setFromCity(journey.getFromCity());
        detailedJourneyDTO.setToCity(journey.getToCity());
        detailedJourneyDTO.setStart(journey.getStart());
        detailedJourneyDTO.setEnd(journey.getEnd());
        if(cars != null) {
            detailedJourneyDTO.setAvailableCars(cars);
        } else {
            detailedJourneyDTO.setAvailableCars(Collections.emptyList());
        }
        if (rooms != null) {
            detailedJourneyDTO.setAvailableRooms(rooms);
        } else {
            detailedJourneyDTO.setAvailableRooms(Collections.emptyList());
        }
        if (tickets != null) {
            detailedJourneyDTO.setAvailableTickets(tickets);
        } else {
            detailedJourneyDTO.setAvailableTickets(Collections.emptyList());
        }

        return detailedJourneyDTO;
    }

    public String buildUrl(String host, int port, String path) {
        return "http://" + host + ":" + port + path;
    }

    private JourneyDTO convertToDTO(Journey journey) {
        return modelMapper.map(journey, JourneyDTO.class);
    }

    private Journey convertToEntity(JourneyDTO journeyDTO) {
        return modelMapper.map(journeyDTO, Journey.class);
    }
}
