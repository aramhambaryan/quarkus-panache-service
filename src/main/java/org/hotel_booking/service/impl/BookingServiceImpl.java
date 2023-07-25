package org.hotel_booking.service.impl;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import lombok.RequiredArgsConstructor;
import org.hibernate.reactive.mutiny.Mutiny;
import org.hotel_booking.domain.dto.response.BookingFullResponse;
import org.hotel_booking.domain.dto.request.BookingRequest;
import org.hotel_booking.domain.entity.Booking;
import org.hotel_booking.domain.exception.RoomNotAvailableException;
import org.hotel_booking.kafka.Party1;
import org.hotel_booking.repository.BookingRepository;
import org.hotel_booking.repository.GuestRepository;
import org.hotel_booking.repository.HotelRoomRepository;
import org.hotel_booking.service.BookingService;
import org.hotel_booking.service.mapper.BookingMapper;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@ApplicationScoped
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final HotelRoomRepository hotelRoomRepository;
    private final BookingRepository bookingRepository;
    private final GuestRepository guestRepository;
    private final BookingMapper bookingMapper;
    private final Party1 party1;


    @Override
    @ReactiveTransactional
    public Uni<Boolean> deleteById(Long id) {
        return bookingRepository.deleteById(id);
    }

    @Override
    public Uni<List<BookingFullResponse>> getAll() {
        return bookingRepository.listAll()
                .map(bookingMapper::toDtoList);
    }

    @Override
    @ReactiveTransactional
    public Uni<BookingFullResponse> add(BookingRequest bookingRequest) {
        Booking booking = new Booking();
        return merge(bookingRequest, booking, false)
                .call(party1::sendBooking);
    }

    @Override
    @ReactiveTransactional
    public Uni<BookingFullResponse> update(Long id, BookingRequest bookingRequest) {
        return bookingRepository.getById(id)
                .flatMap(b -> merge(bookingRequest, b, true));
    }

    private Uni<BookingFullResponse> merge(BookingRequest from, Booking into, boolean isUpdate) {
        return toEntity(from, into)
                .call(this::validate)
                .flatMap(b -> Boolean.TRUE.equals(isUpdate) ?
                        Uni.createFrom().item(b) :
                        bookingRepository.persistAndFlush(b))
                .map(bookingMapper::toDto);
    }


    private Uni<Booking> toEntity(BookingRequest from, Booking into) {
        return hotelRoomRepository.getById(from.getHotelRoomId())
                .invoke(hr -> bookingMapper.updateEntity(into, from)
                                           .setHotelRoom(hr))
                .flatMap(x -> guestRepository.getById(from.getGuestId()))
                .invoke(into::setGuest)
                .replaceWith(() -> into);
    }

    private Uni<Void> validate(Booking booking) {
        return Mutiny.fetch(booking.getHotelRoom().getBookings())
                .invoke(Unchecked.consumer(set -> {
                    if (overlap(booking, set))
                        throw new RoomNotAvailableException(booking.getHotelRoom().getId());}))
                .replaceWithVoid();
    }

    private boolean overlap(Booking b1, Set<Booking> bookings) {
        return bookings.stream()
                .filter(b2 -> !Objects.equals(b1.getGuest().getId(), b2.getGuest().getId()))
                .anyMatch(b2 -> overlap(b1, b2));
    }

    private boolean overlap(Booking b1, Booking b2) {
        return !( b1.getCheckInDate().isAfter(b2.getCheckOutDate().minusDays(1))
                || b2.getCheckInDate().isAfter(b1.getCheckOutDate().minusDays(1)) );
    }
}
