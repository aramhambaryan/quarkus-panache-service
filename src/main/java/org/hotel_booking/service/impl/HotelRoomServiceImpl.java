package org.hotel_booking.service.impl;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import org.hotel_booking.domain.dto.request.HotelRoomRequest;
import org.hotel_booking.domain.dto.response.HotelRoomFullResponse;
import org.hotel_booking.domain.dto.response.HotelRoomResponse;
import org.hotel_booking.repository.BookingRepository;
import org.hotel_booking.repository.HotelRoomRepository;
import org.hotel_booking.service.HotelRoomService;
import org.hotel_booking.service.mapper.HotelRoomMapper;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;


@ApplicationScoped
@RequiredArgsConstructor
public class HotelRoomServiceImpl implements HotelRoomService {

    private final HotelRoomMapper hotelRoomMapper;
    private final HotelRoomRepository hotelRoomRepository;
    private final BookingRepository bookingRepository;


    @Override
    public Uni<HotelRoomResponse> add(HotelRoomRequest hotelRoomRequest) {
        return hotelRoomRepository.persistAndFlush(hotelRoomMapper.toEntity(hotelRoomRequest))
                .map(hotelRoomMapper::toDto);
    }

    @Override
    @ReactiveTransactional
    public Uni<Boolean> deleteById(Long id) {
        return bookingRepository.deleteByHotelRoomId(id)
                .replaceWith(hotelRoomRepository.deleteById(id));
    }

    @Override
    @ReactiveTransactional
    public Uni<HotelRoomResponse> update(Long id, HotelRoomRequest hotelRoomRequest) {
        return hotelRoomRepository.getById(id)
                .map(hr -> hotelRoomMapper.updateEntity(hr, hotelRoomRequest))
                .map(hotelRoomMapper::toDto);
    }

    @Override
    public Uni<HotelRoomFullResponse> getById(Long id) {
        return hotelRoomRepository.getById(id)
                .map(hotelRoomMapper::toFullDto);
    }

    @Override
    public Uni<List<HotelRoomResponse>> getAll() {
        return hotelRoomRepository.listAll()
                .map(hotelRoomMapper::toDtoList);
    }

}
