package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.FilmCinemaRepository;
import ru.job4j.cinema.repository.GenreCinemaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmCinemaServiceImpl implements FilmCinemaService {

    private final FilmCinemaRepository filmCinemaRepository;

    private final GenreCinemaRepository genreCinemaRepository;


    public FilmCinemaServiceImpl(FilmCinemaRepository filmCinemaRepository, GenreCinemaRepository genreCinemaRepository) {
        this.filmCinemaRepository = filmCinemaRepository;
        this.genreCinemaRepository = genreCinemaRepository;
    }


    @Override
    public Optional<FilmDto> getFilmById(int id) {
        var optionalFilm = filmCinemaRepository.findById(id);
        if (optionalFilm.isEmpty()) {
            return Optional.empty();
        }
        var film = optionalFilm.get();
        return Optional.of(convert(film));
    }

    @Override
    public List<FilmDto> getAllFilms() {
        var films = filmCinemaRepository.findAll();
        return films.stream().map(this::convert).collect(Collectors.toList());
    }

    private String getGenre(Film film) {
        var optionalGenre = genreCinemaRepository.findById(film.getGenreId());
        return optionalGenre.isPresent() ? optionalGenre.get().getName() : "Неизвестный жанр";
    }

    private FilmDto convert(Film film) {
        return new FilmDto(film.getName(), film.getDescription(), film.getYear(),
                getGenre(film), film.getMinimalAge(),
                film.getDuration(), film.getFileId());
    }
}
