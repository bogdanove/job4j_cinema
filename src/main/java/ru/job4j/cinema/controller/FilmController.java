package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cinema.service.FilmCinemaService;

@ThreadSafe
@Controller
public class FilmController {

    private final FilmCinemaService filmCinemaService;

    public FilmController(FilmCinemaService filmCinemaService) {
        this.filmCinemaService = filmCinemaService;
    }

    @GetMapping("/films")
    public String getFilms(Model model) {
        var films = filmCinemaService.getAllFilms();
        model.addAttribute("films", films);
        return "films/kinoteka";
    }
}
