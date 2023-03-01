package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.User;

import java.util.Optional;

public interface UserCinemaRepository {

    Optional<User> add(User u);

    Optional<User> findById(int id);

    Optional<User> findUserByEmailAndPassword(String email, String password);
}
