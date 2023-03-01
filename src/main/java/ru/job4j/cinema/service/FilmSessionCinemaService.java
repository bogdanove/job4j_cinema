package ru.job4j.cinema.service;

import ru.job4j.cinema.dto.FilmSessionDto;

import java.util.List;
import java.util.Optional;

public interface FilmSessionCinemaService {

    Optional<FilmSessionDto> getFilmSessionById(int id);

    List<FilmSessionDto> getAllFilmSessions();
}
