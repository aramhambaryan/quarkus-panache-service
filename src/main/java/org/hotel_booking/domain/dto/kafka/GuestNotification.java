package org.hotel_booking.domain.dto.kafka;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GuestNotification {

    String firstName;
    String lastName;
    String middleName;
}
