package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class GenreCinemaRepositoryImpl implements GenreCinemaRepository {

    private final static String FIND_BY_ID = "SELECT * FROM genres WHERE id = ?";

    private final BasicDataSource pool;

    private static final Logger LOG = LoggerFactory.getLogger(GenreCinemaRepositoryImpl.class.getName());

    public GenreCinemaRepositoryImpl(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public Optional<Genre> findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(genreFactory(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    private Genre genreFactory(ResultSet it) throws SQLException {
        return new Genre(it.getInt("id"),
                it.getString("name"));
    }
}
