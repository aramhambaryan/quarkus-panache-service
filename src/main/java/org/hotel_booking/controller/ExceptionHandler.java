package org.hotel_booking.controller;

import org.hotel_booking.domain.exception.RoomNotAvailableException;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

public class ExceptionHandler {

    @ServerExceptionMapper
    public Response roomNotAvailableException(RoomNotAvailableException ex) {
        return Response.status(RestResponse.Status.BAD_REQUEST).entity(ex.getMessage()).build();
    }

    @ServerExceptionMapper
    public Response notFoundException(NotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
    }
}
