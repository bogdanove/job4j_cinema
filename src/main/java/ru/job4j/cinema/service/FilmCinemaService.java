package ru.job4j.cinema.service;

import ru.job4j.cinema.dto.FilmDto;

import java.util.List;
import java.util.Optional;

public interface FilmCinemaService {

    Optional<FilmDto> getFilmById(int id);

    List<FilmDto> getAllFilms();
}
