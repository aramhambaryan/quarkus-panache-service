package org.hotel_booking.domain.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hotel_booking.domain.enums.HotelRoomCategory;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HotelRoomRequest {

    @NotNull
    Integer roomNumber;
    @NotNull
    @Positive
    Integer bedroomCount;
    @NotNull
    HotelRoomCategory category;
    LocalDateTime cleaningTime;
}
