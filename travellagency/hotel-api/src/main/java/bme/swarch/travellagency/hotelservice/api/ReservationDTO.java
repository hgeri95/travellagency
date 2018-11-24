package bme.swarch.travellagency.hotelservice.api;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class ReservationDTO implements Serializable {

    private Long id;

    @NotNull
    private Long roomId;

    @NotNull
    private Date start;

    @NotNull
    private Date end;

    public ReservationDTO() {
    }

    public ReservationDTO(@NotNull Long roomId, @NotNull Date start, @NotNull Date end) {
        this.roomId = roomId;
        this.start = start;
        this.end = end;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
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
}
