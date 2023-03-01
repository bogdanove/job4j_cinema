package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Genre;

import java.util.Optional;

public interface GenreCinemaService {

    Optional<Genre> getGenreById(int id);
}
