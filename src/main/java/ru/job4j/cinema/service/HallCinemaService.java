package ru.job4j.cinema.service;

import ru.job4j.cinema.model.Hall;

import java.util.List;
import java.util.Optional;

public interface HallCinemaService {

    Optional<Hall> getHallById(int id);

    List<Hall> getAllHalls();

    List<Integer> getRows(Hall hall);

    List<Integer> getPlaces(Hall hall);
}
