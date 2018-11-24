package bme.swarch.travellagency.agencyservice.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "reservation_agency")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long journeyId;

    private int numOfPersons;

    @NotBlank
    private String name;

    @NotBlank
    private String idNumber;

    private Long carReservationId;

    private Long roomReservationId;

    @ElementCollection(targetClass = Long.class)
    private List<Long> ticketReservationIds;

    public Reservation() {
    }

    public Reservation(@NotNull Long journeyId, int numOfPersons, @NotBlank String name, @NotBlank String idNumber, Long carReservationId, Long roomReservationId, List<Long> ticketReservationIds) {
        this.journeyId = journeyId;
        this.numOfPersons = numOfPersons;
        this.name = name;
        this.idNumber = idNumber;
        this.carReservationId = carReservationId;
        this.roomReservationId = roomReservationId;
        this.ticketReservationIds = ticketReservationIds;
    }

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
}
