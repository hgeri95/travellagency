package bme.swarch.travellagency.agencyservice.api;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

public class JourneyDTO implements Serializable {

    private Long id;

    private Date start;

    private Date end;

    @NotBlank
    private String fromCity;

    @NotBlank
    private String toCity;

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
}
