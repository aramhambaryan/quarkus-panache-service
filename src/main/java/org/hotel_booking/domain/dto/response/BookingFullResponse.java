package org.hotel_booking.domain.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingFullResponse extends BookingResponse {

    GuestResponse guest;
    HotelRoomResponse hotelRoom;
}
