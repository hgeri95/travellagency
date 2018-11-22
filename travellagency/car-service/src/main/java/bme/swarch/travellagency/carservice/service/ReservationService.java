package bme.swarch.travellagency.carservice.service;

import bme.swarch.travellagency.carservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository repository;


}
