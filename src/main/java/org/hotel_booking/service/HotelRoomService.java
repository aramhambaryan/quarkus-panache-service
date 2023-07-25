package org.hotel_booking.service;

import io.smallrye.mutiny.Uni;
import org.hotel_booking.domain.dto.request.HotelRoomRequest;
import org.hotel_booking.domain.dto.response.HotelRoomFullResponse;
import org.hotel_booking.domain.dto.response.HotelRoomResponse;

import java.util.List;

public interface HotelRoomService {

    /**
     * create a new hotel room with given request
     * @param hotelRoomRequest the request to create a room from
     * @return the created hotel room
     */
    Uni<HotelRoomResponse> add(HotelRoomRequest hotelRoomRequest);

    /**
     * delete the room with given id
     * @param id the id of the room to be deleted
     * @return true or false
     */
    Uni<Boolean> deleteById(Long id);

    /**
     * update the room with given id according to the given request
     * @param id the id of the room to be updated
     * @param hotelRoomRequest the request to update the room with
     * @return the updated room
     * @throws javax.ws.rs.NotFoundException if the room with given id cannot be found
     */
    Uni<HotelRoomResponse> update(Long id, HotelRoomRequest hotelRoomRequest);

    /**
     * get the room with given id
     * @param id the id of the room to get
     * @return the found room
     * @throws javax.ws.rs.NotFoundException if the room cannot be found
     */
    Uni<HotelRoomFullResponse> getById(Long id);

    /**
     * get all the rooms
     * @return all rooms
     */
    Uni<List<HotelRoomResponse>> getAll();
}
