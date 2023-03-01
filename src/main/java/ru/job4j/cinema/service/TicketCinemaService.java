package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Ticket;

import java.util.Optional;

public interface TicketCinemaService {

    Optional<Ticket> save(Ticket ticket);
}
