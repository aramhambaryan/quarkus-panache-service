package org.hotel_booking.domain.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingRequest {

    @NotNull
    Long guestId;
    @NotNull
    Long hotelRoomId;
    @NotNull
    LocalDate checkInDate;
    @NotNull
    LocalDate checkOutDate;
}
