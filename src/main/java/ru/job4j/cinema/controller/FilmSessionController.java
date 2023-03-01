package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.service.FilmSessionCinemaService;

@ThreadSafe
@Controller
public class FilmSessionController {

    private final FilmSessionCinemaService filmSessionCinemaService;

    public FilmSessionController(FilmSessionCinemaService filmSessionCinemaService) {
        this.filmSessionCinemaService = filmSessionCinemaService;
    }

    @GetMapping("/sessions")
    public String getFilmSessions(Model model) {
        var filmSessions = filmSessionCinemaService.getAllFilmSessions();
        model.addAttribute("filmSessions", filmSessions);
        return "sessions/shedule";
    }
}
