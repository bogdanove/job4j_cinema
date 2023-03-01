package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Ticket;

import java.util.Optional;

public interface TicketCinemaRepository {

    Optional<Ticket> add(Ticket t);

    Optional<Ticket> findByRowNumberAndPlaceNumberInSession(int filmSessionId, int rowNumber, int placeNumber);
}