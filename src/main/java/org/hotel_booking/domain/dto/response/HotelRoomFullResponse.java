package org.hotel_booking.domain.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HotelRoomFullResponse extends HotelRoomResponse {

    Set<BookingResponse> bookings;
}
