package org.hotel_booking.service.impl;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import org.hotel_booking.domain.dto.request.GuestRequest;
import org.hotel_booking.domain.dto.response.GuestFullResponse;
import org.hotel_booking.domain.dto.response.GuestResponse;
import org.hotel_booking.domain.entity.Booking;
import org.hotel_booking.domain.entity.Guest;
import org.hotel_booking.repository.BookingRepository;
import org.hotel_booking.repository.GuestRepository;
import org.hotel_booking.service.GuestService;
import org.hotel_booking.service.mapper.GuestMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import java.util.List;

import static org.hotel_booking.util.Utils.NOT_FOUND_MESSAGE;

@ApplicationScoped
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;
    private final BookingRepository bookingRepository;


    @Override
    public Uni<List<GuestFullResponse>> getAll() {
        return guestRepository.findAllFetchBookings()
                .map(guestMapper::toFullDtoList);
    }

    @Override
    @ReactiveTransactional
    public Uni<Boolean> deleteById(Long id) {
        return bookingRepository.deleteByGuestId(id)
                .onItem()
                .ifNull().failWith(() -> new NotFoundException(NOT_FOUND_MESSAGE.apply(
                        Booking.class.getSimpleName(),
                        Guest.class.getSimpleName().toLowerCase(),
                        id)))
                .flatMap(ignore -> guestRepository.deleteById(id));
    }

    @Override
    @ReactiveTransactional
    public Uni<GuestResponse> add(GuestRequest guestRequest) {
        return Uni.createFrom().item(guestMapper.toEntity(guestRequest))
                .flatMap(guestRepository::persistAndFlush)
                .map(guestMapper::toDto);
    }

    @Override
    @ReactiveTransactional
    public Uni<GuestResponse> update(Long id, GuestRequest guestRequest) {
        return guestRepository.getById(id)
                .map(g -> guestMapper.toDto(guestMapper.updateEntity(g, guestRequest)));
    }
}
