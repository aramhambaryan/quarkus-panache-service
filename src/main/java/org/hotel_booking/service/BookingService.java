package org.hotel_booking.service;

import io.smallrye.mutiny.Uni;
import org.hotel_booking.domain.dto.request.BookingRequest;
import org.hotel_booking.domain.dto.response.BookingFullResponse;

import java.util.List;

public interface BookingService {

    /**
     * delete booking by id
     * @param id id of the booking to be deleted
     * @return true or false (successful / not successful)
     */
    Uni<Boolean> deleteById(Long id);

    Uni<List<BookingFullResponse>> getAll();

    /**
     * create a new booking with given request
     * @param bookingRequest request to create a booking from
     * @return the created booking
     * @throws javax.ws.rs.NotFoundException if guest with given {@link BookingRequest#getGuestId()} cannot be found
     * or if room with given {@link BookingRequest#getHotelRoomId()} cannot be found
     */
    Uni<BookingFullResponse> add(BookingRequest bookingRequest);

    /**
     * update booking with given id according to given request
     * @param id id of the booking to update
     * @param bookingRequest request to update the booking from
     * @return the updated booking
     * @throws javax.ws.rs.NotFoundException if the booking with given id is not found
     */
    Uni<BookingFullResponse> update(Long id, BookingRequest bookingRequest);
}
