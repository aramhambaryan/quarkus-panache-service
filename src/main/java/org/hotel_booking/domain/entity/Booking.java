package org.hotel_booking.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "guest_id")
    Guest guest;

    @ManyToOne
    @JoinColumn(name = "hotel_room_id")
    HotelRoom hotelRoom;

    @Column(name = "check_in_date")
    LocalDate checkInDate;

    @Column(name = "check_out_date")
    LocalDate checkOutDate;
}
