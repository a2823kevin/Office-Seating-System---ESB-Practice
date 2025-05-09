package com.a2823kevin.backend.exception;

public class SeatNotFoundException extends RuntimeException {
    public SeatNotFoundException(int seatSeq) {
        super("Seat SEQ not found: " + seatSeq);
    }
}