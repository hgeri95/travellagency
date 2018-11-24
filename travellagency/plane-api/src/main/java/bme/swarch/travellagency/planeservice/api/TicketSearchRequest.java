package bme.swarch.travellagency.planeservice.api;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class TicketSearchRequest implements Serializable {
    @NotBlank
    private String from;
    @NotBlank
    private String to;
    @NotNull
    private Date start;

    public TicketSearchRequest() {
    }

    public TicketSearchRequest(@NotBlank String from, @NotBlank String to, @NotNull Date start) {
        this.from = from;
        this.to = to;
        this.start = start;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }
}
