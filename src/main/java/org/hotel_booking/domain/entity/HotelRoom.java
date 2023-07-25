package org.hotel_booking.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hotel_booking.domain.enums.HotelRoomCategory;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "hotel_room")
public class HotelRoom extends BaseEntity {

    @Column(name = "room_number")
    Integer roomNumber;

    @Column(name = "bedroom_count")
    Integer bedroomCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    HotelRoomCategory category;

    @Column(name = "cleaning_time")
    LocalDateTime cleaningTime;

    @OneToMany(mappedBy = "hotelRoom")
    Set<Booking> bookings;

}
