package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.repository.UserCinemaRepositoryImpl;

import java.util.Optional;

@Service
public class UserUserCinemaServiceImpl implements UserCinemaService {

    private final UserCinemaRepositoryImpl userCinemaRepositoryImpl;

    public UserUserCinemaServiceImpl(UserCinemaRepositoryImpl userCinemaRepositoryImpl) {
        this.userCinemaRepositoryImpl = userCinemaRepositoryImpl;
    }

    @Override
    public Optional<User> add(User user) {
        return userCinemaRepositoryImpl.add(user);
    }

    @Override
    public Optional<User> findById(int id) {
        return userCinemaRepositoryImpl.findById(id);
    }

    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        return userCinemaRepositoryImpl.findUserByEmailAndPassword(email, password);
    }
}
