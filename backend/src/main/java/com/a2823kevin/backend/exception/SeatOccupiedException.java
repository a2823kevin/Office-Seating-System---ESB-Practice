package com.a2823kevin.backend.exception;

public class SeatOccupiedException extends RuntimeException {
    public SeatOccupiedException(String empId, int floorSeatSeq) {
        super(String.format("Seat %d has been occupied by %s", floorSeatSeq, empId));
    }
}
