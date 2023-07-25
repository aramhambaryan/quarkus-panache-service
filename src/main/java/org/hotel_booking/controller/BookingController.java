package org.hotel_booking.controller;

import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import org.hotel_booking.domain.dto.request.BookingRequest;
import org.hotel_booking.domain.dto.response.BookingFullResponse;
import org.hotel_booking.service.BookingService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("bookings")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingController {

    private final BookingService bookingService;

    @GET
    public Uni<List<BookingFullResponse>> getAll() {
        return bookingService.getAll();
    }

    @POST
    public Uni<BookingFullResponse> add(@Valid BookingRequest bookingRequest) {
        return bookingService.add(bookingRequest);
    }

    @PATCH
    @Path("{id}")
    public Uni<BookingFullResponse> update(@PathParam("id") Long id, @Valid BookingRequest bookingRequest) {
        return bookingService.update(id, bookingRequest);
    }

    @DELETE
    @Path("{id}")
    public Uni<Boolean> deleteById(@PathParam("id") Long id) {
        return bookingService.deleteById(id);
    }
}
