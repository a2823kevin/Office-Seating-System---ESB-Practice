package com.a2823kevin.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a2823kevin.backend.dto.ApiResponse;
import com.a2823kevin.backend.dto.SeatingChartDto;
import com.a2823kevin.backend.service.SeatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/seat")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllSeats() {
        List<SeatingChartDto> seats = seatService.getSeats();
        return ResponseEntity.ok(
            ApiResponse.success(
                String.format("find %d seats.", seats.size()), 
                seats
            )
        );
    }
}
