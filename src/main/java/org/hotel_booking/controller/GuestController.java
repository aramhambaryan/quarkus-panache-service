package org.hotel_booking.controller;

import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import org.hotel_booking.domain.dto.request.GuestRequest;
import org.hotel_booking.domain.dto.response.GuestFullResponse;
import org.hotel_booking.domain.dto.response.GuestResponse;
import org.hotel_booking.service.GuestService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


@Path("guests")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GuestController {

    private final GuestService guestService;

    @GET
    public Uni<List<GuestFullResponse>> getAll() {
        return guestService.getAll();
    }

    @POST
    public Uni<GuestResponse> add(@Valid GuestRequest guestRequest) {
        return guestService.add(guestRequest);
    }

    @DELETE
    @Path("{id}")
    public Uni<Boolean> deleteById(@PathParam("id") Long id) {
        return guestService.deleteById(id);
    }

    @PATCH
    @Path("{id}")
    public Uni<GuestResponse> update(@PathParam("id") Long id, @Valid GuestRequest guestRequest) {
        return guestService.update(id, guestRequest);
    }
}
