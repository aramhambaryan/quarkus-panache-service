package org.hotel_booking.domain.dto.response;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hotel_booking.domain.enums.HotelRoomCategory;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class HotelRoomResponse {

    Long id;
    Integer bedroomCount;
    Integer roomNumber;
    HotelRoomCategory category;
    LocalDateTime cleaningTime;
}
