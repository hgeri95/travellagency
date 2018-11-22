package bme.swarch.travellagency.carservice.api;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class CarSearchRequest {

    @NotNull
    private String country;
    @NotNull
    private String city;
    @NotNull
    private Date start;
    @NotNull
    private Date end;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
