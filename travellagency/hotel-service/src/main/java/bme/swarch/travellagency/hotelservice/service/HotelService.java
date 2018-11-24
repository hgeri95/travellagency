package bme.swarch.travellagency.hotelservice.service;

import bme.swarch.travellagency.hotelservice.api.ReservationDTO;
import bme.swarch.travellagency.hotelservice.api.RoomDTO;
import bme.swarch.travellagency.hotelservice.api.RoomSearchRequest;
import bme.swarch.travellagency.hotelservice.exception.ResourceNotFoundException;
import bme.swarch.travellagency.hotelservice.model.Room;
import bme.swarch.travellagency.hotelservice.repository.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationService reservationService;

    @PostConstruct
    public void init() {
        String hun = "Hungary";
        String spn = "Spain";
        String frn = "French";
        String bud = "Budapest";
        String bcn = "Barcelona";
        String prs = "Paris";
        createRoom(hun, bud, 2);
        createRoom(hun, bud, 2);
        createRoom(hun, bud, 4);
        createRoom(spn, bcn, 1);
        createRoom(spn, bcn, 3);
        createRoom(frn, prs, 2);
        createRoom(frn, prs, 6);
    }

    public List<RoomDTO> getAllRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public RoomDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + id));
        return convertToDTO(room);
    }

    public List<RoomDTO> getAllRoomFromPlace(String country, String city) {
        List<Room> rooms = roomRepository.findAllByCountryAndCity(country, city);
        return rooms.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<RoomDTO> getFreeRooms(RoomSearchRequest roomSearchRequest) {
        List<Room> rooms = roomRepository
                .findAllByCountryAndCity(roomSearchRequest.getCountry(), roomSearchRequest.getCity());
        List<Room> freeRooms = rooms.stream()
                .filter(r -> reservationService.isRoomFree(r.getId(), roomSearchRequest.getStart(), roomSearchRequest.getEnd()))
                .collect(Collectors.toList());
        return freeRooms.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private void createRoom(String country, String city, int size) {
        roomRepository.save(new Room(country, city, size));
    }

    private RoomDTO convertToDTO(Room room) {
        return modelMapper.map(room, RoomDTO.class);
    }

    private Room convertToEntity(RoomDTO roomDTO) {
        return modelMapper.map(roomDTO, Room.class);
    }
}
