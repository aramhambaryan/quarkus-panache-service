package org.hotel_booking.service.mapper;

import org.hotel_booking.config.MappingConfig;
import org.hotel_booking.domain.dto.kafka.BookingNotification;
import org.hotel_booking.domain.dto.request.BookingRequest;
import org.hotel_booking.domain.dto.response.BookingFullResponse;
import org.hotel_booking.domain.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MappingConfig.class)
public interface BookingMapper extends CommonMapper<Booking, BookingFullResponse, BookingRequest> {

    @Mapping(target = "hotelRoomId", source = "bookingFullResponse.hotelRoom.id")
    BookingNotification toBookingNotification(BookingFullResponse bookingFullResponse);
}
