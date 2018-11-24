package bme.swarch.travellagency.planeservice.service;

import bme.swarch.travellagency.planeservice.api.TicketDTO;
import bme.swarch.travellagency.planeservice.exception.ResourceNotFoundException;
import bme.swarch.travellagency.planeservice.model.Ticket;
import bme.swarch.travellagency.planeservice.repository.TicketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private TicketRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    public void init() throws ParseException {
        String bud = "Budapest";
        String bcn = "Barcelona";
        String prs = "Paris";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        createTicket(bud, bcn, dateFormat.parse("2018-12-01 08:10"));
        createTicket(bud, bcn, dateFormat.parse("2018-12-02 08:10"));
        createTicket(bud, bcn, dateFormat.parse("2018-12-03 08:10"));
        createTicket(bcn, bud, dateFormat.parse("2018-12-02 08:10"));
        createTicket(bcn, bud, dateFormat.parse("2018-12-03 08:10"));
        createTicket(bcn, bud, dateFormat.parse("2018-12-04 08:10"));
        createTicket(bud, prs, dateFormat.parse("2018-12-02 08:10"));
        createTicket(bud, prs, dateFormat.parse("2018-12-03 08:10"));
        createTicket(bud, prs, dateFormat.parse("2018-12-04 08:10"));
        createTicket(bud, prs, dateFormat.parse("2018-12-05 08:10"));
        createTicket(prs, bud, dateFormat.parse("2018-12-03 08:10"));
        createTicket(prs, bud, dateFormat.parse("2018-12-05 08:10"));
        createTicket(prs, bud, dateFormat.parse("2018-12-06 08:10"));
    }

    public List<TicketDTO> getAllTickets() {
        List<Ticket> tickets = repository.findAll();
        return tickets.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public TicketDTO findById(Long id) {
        Ticket ticket = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));
        return convertToDTO(ticket);
    }

    public List<TicketDTO> getAllByFromAndTo(String from, String to) {
        List<Ticket> tickets = repository.findAllByFromCityAndToCity(from, to);
        return tickets.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<TicketDTO> searchFree(String from, String to, Date start) {
        List<Ticket> tickets = repository.findAllByFromCityAndToCity(from, to);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String startDate = dateFormat.format(start);
        return tickets.stream().filter(t -> t.isFree() && dateFormat.format(t.getStart()).equals(startDate))
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    public TicketDTO reserveTicket(Long id) {
        Ticket ticket = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));
        ticket.setFree(false);
        return convertToDTO(repository.save(ticket));
    }

    public TicketDTO convertToDTO(Ticket ticket) {
        return modelMapper.map(ticket, TicketDTO.class);
    }

    public Ticket convertToEntity(TicketDTO ticketDTO) {
        return modelMapper.map(ticketDTO, Ticket.class);
    }

    private void createTicket(String from, String to, Date start) {
        repository.save(new Ticket(from, to, start, true));
    }
}
