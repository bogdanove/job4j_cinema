package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class FileCinemaRepositoryImpl implements FileCinemaRepository {

    private final static String FIND_BY_ID = "SELECT * FROM files WHERE id = ?";

    private final BasicDataSource pool;

    private static final Logger LOG = LoggerFactory.getLogger(FileCinemaRepositoryImpl.class.getName());

    public FileCinemaRepositoryImpl(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public Optional<File> findById(int id) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(fileFactory(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    private File fileFactory(ResultSet it) throws SQLException {
        return new File(it.getInt("id"),
                it.getString("name"),
                it.getString("path"));
    }
}
