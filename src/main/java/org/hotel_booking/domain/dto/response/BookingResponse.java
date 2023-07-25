package org.hotel_booking.domain.dto.response;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class BookingResponse {

	Long id;
	LocalDate checkInDate;
	LocalDate checkOutDate;
}
