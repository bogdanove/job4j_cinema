package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.FilmCinemaRepository;
import ru.job4j.cinema.repository.FilmSessionCinemaRepository;
import ru.job4j.cinema.repository.HallCinemaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmSessionCinemaServiceImpl implements FilmSessionCinemaService {

    private final FilmSessionCinemaRepository filmSessionCinemaRepository;

    private final FilmCinemaRepository filmCinemaRepository;

    private final HallCinemaRepository hallCinemaRepository;

    public FilmSessionCinemaServiceImpl(FilmSessionCinemaRepository filmSessionCinemaRepository, FilmCinemaRepository filmCinemaRepository, HallCinemaRepository hallCinemaRepository) {
        this.filmSessionCinemaRepository = filmSessionCinemaRepository;
        this.filmCinemaRepository = filmCinemaRepository;
        this.hallCinemaRepository = hallCinemaRepository;
    }

    @Override
    public Optional<FilmSessionDto> getFilmSessionById(int id) {
        var optionalFilmSession = filmSessionCinemaRepository.findById(id);
        if (optionalFilmSession.isEmpty()) {
            return Optional.empty();
        }
        var filmSession = optionalFilmSession.get();
        return Optional.of(convert(filmSession));
    }

    @Override
    public List<FilmSessionDto> getAllFilmSessions() {
        var filmSessions = filmSessionCinemaRepository.findAll();
        return filmSessions.stream().map(this::convert).collect(Collectors.toList());
    }

    private String getFilm(FilmSession filmSession) {
        var optionalFilm = filmCinemaRepository.findById(filmSession.getFilmId());
        return optionalFilm.isPresent() ? optionalFilm.get().getName() : "Неизвестный фильм";
    }

    private String getHall(FilmSession filmSession) {
        var optionalHall = hallCinemaRepository.findById(filmSession.getHallsId());
        return optionalHall.isPresent() ? optionalHall.get().getName() : "Неизвестный кинозал";
    }

    private FilmSessionDto convert(FilmSession filmSession) {
        return new FilmSessionDto(filmSession.getId(), getFilm(filmSession), getHall(filmSession), filmSession.getHallsId(),
                filmSession.getStartTime(), filmSession.getEndTime(), filmSession.getPrice());
    }
}
