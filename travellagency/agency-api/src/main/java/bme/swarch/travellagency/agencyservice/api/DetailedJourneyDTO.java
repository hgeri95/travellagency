package bme.swarch.travellagency.agencyservice.api;

import bme.swarch.travellagency.carservice.api.CarDTO;
import bme.swarch.travellagency.hotelservice.api.RoomDTO;
import bme.swarch.travellagency.planeservice.api.TicketDTO;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DetailedJourneyDTO implements Serializable {
    private Long id;

    private Date start;

    private Date end;

    @NotBlank
    private String fromCity;

    @NotBlank
    private String toCity;

    private List<CarDTO> availableCars;

    private List<RoomDTO> availableRooms;

    private List<TicketDTO> availableTickets;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public List<CarDTO> getAvailableCars() {
        return availableCars;
    }

    public void setAvailableCars(List<CarDTO> availableCars) {
        this.availableCars = availableCars;
    }

    public List<RoomDTO> getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(List<RoomDTO> availableRooms) {
        this.availableRooms = availableRooms;
    }

    public List<TicketDTO> getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(List<TicketDTO> availableTickets) {
        this.availableTickets = availableTickets;
    }
}
