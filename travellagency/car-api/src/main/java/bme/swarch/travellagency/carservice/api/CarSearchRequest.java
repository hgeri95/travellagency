package bme.swarch.travellagency.carservice.api;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class CarSearchRequest {

    @NotNull
    private String city;
    @NotNull
    private Date start;
    @NotNull
    private Date end;

    public CarSearchRequest() {
    }

    public CarSearchRequest(@NotNull String city, @NotNull Date start, @NotNull Date end) {
        this.city = city;
        this.start = start;
        this.end = end;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
