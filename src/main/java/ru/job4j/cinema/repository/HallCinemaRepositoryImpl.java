package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Hall;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class HallCinemaRepositoryImpl implements HallCinemaRepository {

    private final static String FIND_ALL = "SELECT * FROM halls";
    private final static String FIND_BY_ID = "SELECT * FROM halls WHERE id = ?";

    private final BasicDataSource pool;

    private static final Logger LOG = LoggerFactory.getLogger(HallCinemaRepositoryImpl.class.getName());

    public HallCinemaRepositoryImpl(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public List<Hall> findAll() {
        List<Hall> halls = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_ALL)
        ) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    halls.add(hallFactory(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return halls;
    }

    @Override
    public Optional<Hall> findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(hallFactory(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    private Hall hallFactory(ResultSet it) throws SQLException {
        return new Hall(it.getInt("id"),
                it.getString("name"),
                it.getInt("row_count"),
                it.getInt("place_count"),
                it.getString("description"));
    }
}
