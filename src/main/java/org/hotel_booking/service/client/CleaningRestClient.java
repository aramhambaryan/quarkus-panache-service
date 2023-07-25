package org.hotel_booking.service.client;

import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.hotel_booking.domain.dto.response.CleaningResponse;
import org.hotel_booking.util.MyTimeOut;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("api")
@RegisterRestClient(configKey = "cleaning-time")
public interface CleaningRestClient {

    @MyTimeOut
    @GET
    @Path("cleanings")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<List<CleaningResponse>> getPartData(@QueryParam("part") Integer part);
}
