package bme.swarch.travellagency.hotelservice.api;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class RoomDTO implements Serializable {

    private Long id;

   // @NotBlank
    //private String country;

    @NotBlank
    private String city;

    private int size;

    public RoomDTO() {
    }

    public RoomDTO(/*@NotBlank String country,*/ @NotBlank String city, int size) {
      //  this.country = country;
        this.city = city;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
/*
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
*/
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
