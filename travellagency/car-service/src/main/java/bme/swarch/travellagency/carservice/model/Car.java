package bme.swarch.travellagency.carservice.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "cars")
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String type;

    private int seats;

    private int bootSize;

    @NotBlank
    private boolean isFree;

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

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Car{");
        sb.append("id=").append(id);
        sb.append(", type='").append(type).append('\'');
        sb.append(", seats=").append(seats);
        sb.append(", bootSize=").append(bootSize);
        sb.append(", isFree=").append(isFree);
        sb.append('}');
        return sb.toString();
    }
}
