package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.HallCinemaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
public class HallCinemaServiceImpl implements HallCinemaService {

    private final HallCinemaRepository hallCinemaRepository;

    public HallCinemaServiceImpl(HallCinemaRepository hallCinemaRepository) {
        this.hallCinemaRepository = hallCinemaRepository;
    }

    @Override
    public Optional<Hall> getHallById(int id) {
        return hallCinemaRepository.findById(id);
    }

    @Override
    public List<Hall> getAllHalls() {
        return hallCinemaRepository.findAll();
    }

    @Override
    public List<Integer> getRows(Hall hall) {
        return IntStream.rangeClosed(1, hall.getRowCount()).boxed().toList();
    }

    @Override
    public List<Integer> getPlaces(Hall hall) {
        return IntStream.rangeClosed(1, hall.getPlaceCount()).boxed().toList();
    }
}
