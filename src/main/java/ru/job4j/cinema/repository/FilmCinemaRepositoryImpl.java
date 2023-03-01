package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FilmCinemaRepositoryImpl implements FilmCinemaRepository {

    private final static String FIND_ALL = "SELECT * FROM films";
    private final static String FIND_BY_ID = "SELECT * FROM films WHERE id = ?";

    private final BasicDataSource pool;

    private static final Logger LOG = LoggerFactory.getLogger(FilmCinemaRepositoryImpl.class.getName());

    public FilmCinemaRepositoryImpl(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public List<Film> findAll() {
        List<Film> films = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    films.add(filmFactory(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return films;
    }

    @Override
    public Optional<Film> findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(filmFactory(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    private Film filmFactory(ResultSet it) throws SQLException {
        return new Film(it.getInt("id"),
                it.getString("name"),
                it.getString("description"),
                it.getInt("year"),
                it.getInt("genre_id"),
                it.getInt("minimal_age"),
                it.getInt("duration_in_minutes"),
                it.getInt("file_id"));
    }
}
