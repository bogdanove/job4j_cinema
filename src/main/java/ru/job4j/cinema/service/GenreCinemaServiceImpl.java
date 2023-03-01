package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.repository.GenreCinemaRepository;

import java.util.Optional;

@Service
public class GenreCinemaServiceImpl implements GenreCinemaService {

    private final GenreCinemaRepository genreCinemaRepository;

    public GenreCinemaServiceImpl(GenreCinemaRepository genreCinemaRepository) {
        this.genreCinemaRepository = genreCinemaRepository;
    }

    @Override
    public Optional<Genre> getGenreById(int id) {
        return genreCinemaRepository.findById(id);
    }
}
