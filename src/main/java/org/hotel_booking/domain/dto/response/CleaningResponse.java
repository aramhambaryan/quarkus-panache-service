package org.hotel_booking.domain.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CleaningResponse {

    Long id;
    Integer roomNumber;
    LocalDateTime cleaningTime;
}
