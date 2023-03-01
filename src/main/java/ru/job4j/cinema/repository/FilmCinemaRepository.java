package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmCinemaRepository {

    List<Film> findAll();

    Optional<Film> findById(int id);
}