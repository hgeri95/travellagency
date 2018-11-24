package bme.swarch.travellagency.agencyservice.api;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

public class ReservationResponseDTO implements Serializable {
    private Long id;

    private Long journeyId;

    private int numOfPersons;

    private Long carReservationId;

    private Long roomReservationId;

    private List<Long> ticketReservationIds;

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

    public Long getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(Long journeyId) {
        this.journeyId = journeyId;
    }

    public int getNumOfPersons() {
        return numOfPersons;
    }

    public void setNumOfPersons(int numOfPersons) {
        this.numOfPersons = numOfPersons;
    }

    public Long getCarReservationId() {
        return carReservationId;
    }

    public void setCarReservationId(Long carReservationId) {
        this.carReservationId = carReservationId;
    }

    public Long getRoomReservationId() {
        return roomReservationId;
    }

    public void setRoomReservationId(Long roomReservationId) {
        this.roomReservationId = roomReservationId;
    }

    public List<Long> getTicketReservationIds() {
        return ticketReservationIds;
    }

    public void setTicketReservationIds(List<Long> ticketReservationIds) {
        this.ticketReservationIds = ticketReservationIds;
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
