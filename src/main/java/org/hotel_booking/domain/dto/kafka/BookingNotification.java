package org.hotel_booking.domain.dto.kafka;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingNotification {

    Long hotelRoomId;
    LocalDate checkInDate;
    LocalDate checkOutDate;
}
