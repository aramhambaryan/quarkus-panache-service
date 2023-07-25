package org.hotel_booking.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import org.hotel_booking.domain.entity.Booking;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;

import static org.hotel_booking.util.Utils.NOT_FOUND_MESSAGE;

@ApplicationScoped
public class BookingRepository implements PanacheRepositoryBase<Booking, Long> {

    public Uni<Long> deleteByGuestId(Long guestId) {
        return delete("guest.id", guestId)
                .map(n -> {
                    if (n <= 0)
                        return null;
                    else return n;
                });
    }

    public Uni<Long> deleteByHotelRoomId(Long hotelRoomId) {
        return delete("hotelRoom.id", hotelRoomId);
    }

    public Uni<Booking> getById(Long id) {
        return findById(id)
                .onItem()
                .ifNull()
                .failWith(() -> new NotFoundException(NOT_FOUND_MESSAGE.apply(
                        Booking.class.getSimpleName(),
                        Booking.class.getSimpleName().toLowerCase(),
                        id)));
    }
}