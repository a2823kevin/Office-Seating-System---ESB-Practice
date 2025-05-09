package com.a2823kevin.backend.dto;

import com.a2823kevin.backend.model.SeatingChart;

import lombok.Data;

@Data
public class SeatingChartDto {
    private int floorSeatSeq;
    private int floorNo;
    private int seatNo;

    public SeatingChartDto(SeatingChart seatingChart) {
        setFloorSeatSeq(seatingChart.getFloorSeatSeq());
        setFloorNo(seatingChart.getFloorNo());
        setSeatNo(seatingChart.getSeatNo());
    }
}