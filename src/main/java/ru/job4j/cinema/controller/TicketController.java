package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.FilmSessionCinemaService;
import ru.job4j.cinema.service.HallCinemaService;
import ru.job4j.cinema.service.TicketCinemaService;

@ThreadSafe
@Controller
public class TicketController {

    private final FilmSessionCinemaService filmSessionCinemaService;

    private final TicketCinemaService ticketCinemaService;

    private final HallCinemaService hallCinemaService;

    public TicketController(FilmSessionCinemaService filmSessionCinemaService, TicketCinemaService ticketCinemaService, HallCinemaService hallCinemaService) {
        this.filmSessionCinemaService = filmSessionCinemaService;
        this.ticketCinemaService = ticketCinemaService;
        this.hallCinemaService = hallCinemaService;
    }

    @GetMapping("/ticket/{id}")
    public String getById(Model model, @PathVariable int id) {
        var optFilmSession = filmSessionCinemaService.getFilmSessionById(id);
        if (optFilmSession.isEmpty()) {
            model.addAttribute("message", "Указанный киносеанс не найден");
            return "errors/404";
        }
        var filmSession = optFilmSession.get();
        var hall = hallCinemaService.getHallById(filmSession.getHallId()).get();
        var rows = hallCinemaService.getRows(hall);
        var places = hallCinemaService.getPlaces(hall);
        model.addAttribute("rows", rows);
        model.addAttribute("places", places);
        model.addAttribute("filmSession", filmSession);
        model.addAttribute("ticket", new Ticket());
        return "tickets/buyTicket";
    }

    @PostMapping("/ticket/buy")
    public String buyTicket(@ModelAttribute Ticket ticket, Model model) {
        try {
            var optSavedTicket = ticketCinemaService.save(ticket);
            if (optSavedTicket.isEmpty()) {
                model.addAttribute("message", """
                        Не удалось приобрести билет на заданное место. Вероятно оно уже занято.
                        Перейдите на страницу бронирования билетов и попробуйте снова.
                        """);
                return "errors/404";
            }
            model.addAttribute("message", "Вы успешно приобрели билет."
                    + System.lineSeparator() + "Ваш ряд: " + ticket.getRowNumber() + System.lineSeparator()
                    + "Ваше место: " + ticket.getPlaceNumber());
            return "success/done";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }
}
