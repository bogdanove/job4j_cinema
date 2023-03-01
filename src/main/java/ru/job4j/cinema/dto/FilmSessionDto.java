package ru.job4j.cinema.dto;

import java.time.LocalDateTime;

public class FilmSessionDto {

        private int id;

        private String film;

        private String hall;

        private int hallId;

        private LocalDateTime startTime;

        private LocalDateTime endTime;

        private int price;

        public FilmSessionDto(int id, String film, String hall, int hallId, LocalDateTime startTime, LocalDateTime endTime, int price) {
            this.id = id;
            this.film = film;
            this.hall = hall;
            this.hallId = hallId;
            this.startTime = startTime;
            this.endTime = endTime;
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFilm() {
            return film;
        }

        public void setFilm(String film) {
            this.film = film;
        }

        public String getHall() {
            return hall;
        }

        public void setHall(String hall) {
            this.hall = hall;
        }

        public int getHallId() {
            return hallId;
        }

        public void setHallId(int hallId) {
            this.hallId = hallId;
        }

        public LocalDateTime getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
        }

        public LocalDateTime getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
}
