package org.hotel_booking.domain.dto.kafka;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class KafkaNotification {

    GuestNotification guestNotification;
    BookingNotification bookingNotification;
    LocalDateTime sendTime;
}
