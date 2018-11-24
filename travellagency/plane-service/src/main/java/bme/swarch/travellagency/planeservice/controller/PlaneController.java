package bme.swarch.travellagency.planeservice.controller;

import bme.swarch.travellagency.planeservice.api.TicketDTO;
import bme.swarch.travellagency.planeservice.api.TicketSearchRequest;
import bme.swarch.travellagency.planeservice.exception.BadRequestException;
import bme.swarch.travellagency.planeservice.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class PlaneController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/all")
    public List<TicketDTO> getAll() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public TicketDTO getById(@PathVariable("id") Long id) {
        return ticketService.findById(id);
    }

    @GetMapping("/{from}/{to}")
    public List<TicketDTO> getAllByFromTo(@PathVariable("from") String from, @PathVariable("to") String to) {
        if (from != null && to != null) {
            return ticketService.getAllByFromAndTo(from, to);
        } else {
            throw new BadRequestException("Path variable /{from}/{to} cannot be null");
        }
    }

    @PostMapping("/free")
    public List<TicketDTO> searchFreeTickets(@Valid @RequestBody TicketSearchRequest searchRequest) {
        return ticketService.searchFree(searchRequest.getFrom(), searchRequest.getTo(), searchRequest.getStart());
    }

    @PostMapping("/reservation/{id}")
    public TicketDTO reserveTicket(@PathVariable("id") Long id) {
        return ticketService.reserveTicket(id);
    }

}
