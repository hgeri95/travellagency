package bme.swarch.travellagency.carservice.api;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class CarDTO implements Serializable {

    private Long id;

    @NotBlank
    private String type;

    private int seats;

    private int bootSize;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getBootSize() {
        return bootSize;
    }

    public void setBootSize(int bootSize) {
        this.bootSize = bootSize;
    }

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
}
