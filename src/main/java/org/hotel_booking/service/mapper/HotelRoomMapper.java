package org.hotel_booking.service.mapper;

import org.hotel_booking.config.MappingConfig;
import org.hotel_booking.domain.dto.request.HotelRoomRequest;
import org.hotel_booking.domain.dto.response.HotelRoomFullResponse;
import org.hotel_booking.domain.dto.response.HotelRoomResponse;
import org.hotel_booking.domain.entity.HotelRoom;
import org.mapstruct.*;

@Mapper(config = MappingConfig.class)
public interface HotelRoomMapper extends CommonMapper<HotelRoom, HotelRoomResponse, HotelRoomRequest> {
    @Named("toDto")
    HotelRoomResponse toDto(HotelRoom hotelRoom);
    @IterableMapping(qualifiedByName = "toDto")
    HotelRoomFullResponse toFullDto(HotelRoom hotelRoom);

}
