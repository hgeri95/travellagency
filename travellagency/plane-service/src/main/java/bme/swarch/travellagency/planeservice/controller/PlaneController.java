package bme.swarch.travellagency.planeservice.controller;

import bme.swarch.travellagency.planeservice.api.TicketDTO;
import bme.swarch.travellagency.planeservice.api.TicketSearchRequest;
import bme.swarch.travellagency.planeservice.exception.BadRequestException;
import bme.swarch.travellagency.planeservice.service.TicketService;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("Get all tickets")
    public List<TicketDTO> getAll() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    @ApiOperation("Get ticket by ticket id")
    public TicketDTO getById(@PathVariable("id") Long id) {
        return ticketService.findById(id);
    }

    @GetMapping("/{from}/{to}")
    @ApiOperation("Get all tickets with the given from and to params")
    public List<TicketDTO> getAllByFromTo(@PathVariable("from") String from, @PathVariable("to") String to) {
        if (from != null && to != null) {
            return ticketService.getAllByFromAndTo(from, to);
        } else {
            throw new BadRequestException("Path variable /{from}/{to} cannot be null");
        }
    }

    @PostMapping("/free")
    @ApiOperation("Search for free tickets")
    public List<TicketDTO> searchFreeTickets(@Valid @RequestBody TicketSearchRequest searchRequest) {
        return ticketService.searchFree(searchRequest.getFrom(), searchRequest.getTo(), searchRequest.getStart());
    }

    @PostMapping("/reservation/{id}")
    @ApiOperation("Get reservation by ticket id")
    public TicketDTO reserveTicket(@PathVariable("id") Long id) {
        return ticketService.reserveTicket(id);
    }

}
