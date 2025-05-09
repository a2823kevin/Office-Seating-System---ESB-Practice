package com.a2823kevin.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.a2823kevin.backend.exception.SeatNotFoundException;
import com.a2823kevin.backend.model.SeatingChart;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SeatRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<SeatingChart> getSeats() {
        return jdbcTemplate.query("CALL getSeats()", seatingChartRowMapper);
    }

    
    public SeatingChart findSeatBySeq(int seatSeq) throws SeatNotFoundException {
        List<SeatingChart> result = jdbcTemplate.query(
            "CALL getSeatByFloorSeatSeq(?)",
            seatingChartRowMapper,
            seatSeq
        );

        Optional<SeatingChart> seat = result.stream().findFirst();
        seat.orElseThrow(() -> new SeatNotFoundException(seatSeq));
        return seat.get();
    }

    private RowMapper<SeatingChart> seatingChartRowMapper = (rs, rowNum) -> {
        SeatingChart seat = new SeatingChart();
        seat.setFloorSeatSeq(rs.getInt("FLOOR_SEAT_SEQ"));
        seat.setFloorNo(rs.getInt("FLOOR_NO"));
        seat.setSeatNo(rs.getInt("SEAT_NO"));
        return seat;
    };
}