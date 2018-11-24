package bme.swarch.travellagency.hotelservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private String msg;

    public ResourceNotFoundException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
