package org.hotel_booking.service.mapper;

import org.hotel_booking.config.MappingConfig;
import org.hotel_booking.domain.dto.kafka.GuestNotification;
import org.hotel_booking.domain.dto.request.GuestRequest;
import org.hotel_booking.domain.dto.response.GuestFullResponse;
import org.hotel_booking.domain.dto.response.GuestResponse;
import org.hotel_booking.domain.entity.Guest;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MappingConfig.class)
public interface GuestMapper extends CommonMapper<Guest, GuestResponse, GuestRequest> {

    List<GuestFullResponse> toFullDtoList(List<Guest> guests);
    GuestNotification toGuestNotification(GuestResponse guest);

}
