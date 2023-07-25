package org.hotel_booking.controller;

import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import org.hotel_booking.domain.dto.request.HotelRoomRequest;
import org.hotel_booking.domain.dto.response.HotelRoomFullResponse;
import org.hotel_booking.domain.dto.response.HotelRoomResponse;
import org.hotel_booking.service.HotelRoomService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("hotel-rooms")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HotelRoomController {

    private final HotelRoomService hotelRoomService;

    @GET
    @Path("{id}")
    public Uni<HotelRoomFullResponse> getById(@PathParam("id") Long id) {
        return hotelRoomService.getById(id);
    }

    @GET
    public Uni<List<HotelRoomResponse>> getAll() {
        return hotelRoomService.getAll();
    }

    @POST
    public Uni<HotelRoomResponse> add(@Valid HotelRoomRequest hotelRoomRequest) {
        return hotelRoomService.add(hotelRoomRequest);
    }

    @DELETE
    @Path("{id}")
    public Uni<Boolean> deleteById(@PathParam("id") Long id) {
        return hotelRoomService.deleteById(id);
    }

    @PATCH
    @Path("{id}")
    public Uni<HotelRoomResponse> update(@PathParam("id") Long id, @Valid HotelRoomRequest hotelRoomRequest) {
        return hotelRoomService.update(id, hotelRoomRequest);
    }
}
