package ru.job4j.cinema.service;

import ru.job4j.cinema.model.User;

import java.util.Optional;

public interface UserCinemaService {

    Optional<User> add(User user);

    Optional<User> findById(int id);

    Optional<User> findUserByEmailAndPassword(String email, String password);
}
