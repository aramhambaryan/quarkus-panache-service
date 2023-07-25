package org.hotel_booking.domain.exception;

public class RoomNotAvailableException extends RuntimeException {

    public RoomNotAvailableException(Long id) {
        super(String.format("Room with id '%d' is not available at given period", id));
    }
}
