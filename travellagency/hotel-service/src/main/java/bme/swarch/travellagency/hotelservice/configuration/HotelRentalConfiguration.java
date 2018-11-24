package bme.swarch.travellagency.hotelservice.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HotelRentalConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
