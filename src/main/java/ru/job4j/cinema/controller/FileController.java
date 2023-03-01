package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.job4j.cinema.service.FileCinemaService;

@ThreadSafe
@Controller
public class FileController {

    private final FileCinemaService fileCinemaService;

    public FileController(FileCinemaService fileCinemaService) {
        this.fileCinemaService = fileCinemaService;
    }

    @GetMapping("/posters/{id}")
    public ResponseEntity<?> getFileById(@PathVariable int id) {
        var contentOptional = fileCinemaService.getFileById(id);
        if (contentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(contentOptional.get().getContent());
    }
}
