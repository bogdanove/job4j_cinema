package ru.job4j.cinema.repository;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
public class TicketCinemaRepositoryImpl implements TicketCinemaRepository {

    private final static String ADD = "INSERT INTO tickets(session_id, row_number, place_number, user_id) VALUES (?, ?, ?, ?)";
    private final static String FIND_BY_ID = "SELECT * FROM tickets WHERE session_id = ? AND row_number = ? AND place_number = ?";

    private final BasicDataSource pool;

    private static final Logger LOG = LoggerFactory.getLogger(TicketCinemaRepositoryImpl.class.getName());

    public TicketCinemaRepositoryImpl(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public Optional<Ticket> add(Ticket ticket) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(ADD,
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            ps.setInt(1, ticket.getSessionId());
            ps.setInt(2, ticket.getRowNumber());
            ps.setInt(3, ticket.getPlaceNumber());
            ps.setInt(4, ticket.getUserId());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    ticket.setId(id.getInt(1));
                }
            }
            return Optional.of(ticket);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Ticket> findByRowNumberAndPlaceNumberInSession(int filmSessionId, int rowNumber, int placeNumber) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(FIND_BY_ID)
        ) {
            ps.setInt(1, filmSessionId);
            ps.setInt(1, rowNumber);
            ps.setInt(1, placeNumber);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return Optional.of(ticketFactory(it));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    private Ticket ticketFactory(ResultSet it) throws SQLException {
        return new Ticket(it.getInt("id"),
                it.getInt("session_id"),
                it.getInt("row_number"),
                it.getInt("place_number"),
                it.getInt("user_id"));
    }
}
