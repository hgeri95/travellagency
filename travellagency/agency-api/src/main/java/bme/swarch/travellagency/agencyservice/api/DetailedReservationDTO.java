package bme.swarch.travellagency.agencyservice.api;

import bme.swarch.travellagency.carservice.api.ReservationDTO;
import bme.swarch.travellagency.planeservice.api.TicketDTO;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

public class DetailedReservationDTO implements Serializable {

    private Long id;

    private JourneyDTO journey;

    private int numOfPersons;

    private ReservationDTO carReservation;

    private bme.swarch.travellagency.hotelservice.api.ReservationDTO roomReservation;

    private List<TicketDTO> tickets;

    @NotBlank
    private String name;

    @NotBlank
    private String idNumber;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JourneyDTO getJourney() {
        return journey;
    }

    public void setJourney(JourneyDTO journey) {
        this.journey = journey;
    }

    public int getNumOfPersons() {
        return numOfPersons;
    }

    public void setNumOfPersons(int numOfPersons) {
        this.numOfPersons = numOfPersons;
    }

    public ReservationDTO getCarReservation() {
        return carReservation;
    }

    public void setCarReservation(ReservationDTO carReservation) {
        this.carReservation = carReservation;
    }

    public bme.swarch.travellagency.hotelservice.api.ReservationDTO getRoomReservation() {
        return roomReservation;
    }

    public void setRoomReservation(bme.swarch.travellagency.hotelservice.api.ReservationDTO roomReservation) {
        this.roomReservation = roomReservation;
    }

    public List<TicketDTO> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketDTO> tickets) {
        this.tickets = tickets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
