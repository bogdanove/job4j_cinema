package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketCinemaRepository;

import java.util.Optional;

@Service
public class TicketCinemaServiceImpl implements TicketCinemaService {

    private final TicketCinemaRepository ticketCinemaRepository;

    public TicketCinemaServiceImpl(TicketCinemaRepository ticketCinemaRepository) {
        this.ticketCinemaRepository = ticketCinemaRepository;
    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {
        return ticketCinemaRepository.add(ticket);
    }
}
