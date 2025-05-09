package com.a2823kevin.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.a2823kevin.backend.dto.SeatingChartDto;
import com.a2823kevin.backend.repository.SeatRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;

    public List<SeatingChartDto> getSeats() {
        return seatRepository.getSeats()
            .stream()
            .map(seat->new SeatingChartDto(seat))
            .toList();
    }
}