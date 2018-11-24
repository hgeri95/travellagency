package bme.swarch.travellagency.agencyservice.api;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

public class JourneySearchRequest implements Serializable {

    @NotBlank
    private String fromCity;

    @NotBlank
    private String toCity;

    private Date start;

    private Date end;

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
