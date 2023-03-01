package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.FilmSession;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class FilmSessionCinemaRepositoryImpl implements FilmSessionCinemaRepository {

    private final static String FIND_ALL = "SELECT * FROM film_sessions";
    private final static String FIND_BY_ID = "SELECT * FROM film_sessions WHERE id = ?";

    private final BasicDataSource pool;

    private static final Logger LOG = LoggerFactory.getLogger(FilmSessionCinemaRepositoryImpl.class.getName());

    public FilmSessionCinemaRepositoryImpl(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public List<FilmSession> findAll() {
        List<FilmSession> filmSessions = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    filmSessions.add(filmSessionFactory(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return filmSessions;
    }

    @Override
    public Optional<FilmSession> findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(filmSessionFactory(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    private FilmSession filmSessionFactory(ResultSet it) throws SQLException {
        return new FilmSession(it.getInt("id"),
                it.getInt("film_id"),
                it.getInt("halls_id"),
                it.getTimestamp("start_time").toLocalDateTime(),
                it.getTimestamp("end_time").toLocalDateTime(),
                it.getInt("price"));
    }
}
