package org.hotel_booking.domain.dto.response;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class BookingResponseWithRoom extends BookingResponse {

    HotelRoomResponse hotelRoom;
}
