package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.repository.FileCinemaRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class FileCinemaServiceImpl implements FileCinemaService{

    private final FileCinemaRepository fileCinemaRepository;

    public FileCinemaServiceImpl(FileCinemaRepository fileCinemaRepository) {
        this.fileCinemaRepository = fileCinemaRepository;
    }


    @Override
    public Optional<FileDto> getFileById(int id) {
        var file = fileCinemaRepository.findById(id);
        if (file.isEmpty()) {
            return Optional.empty();
        }
        var content = readFileAsBytes(file.get().getPath());
        return Optional.of(new FileDto(file.get().getName(), content));
    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
