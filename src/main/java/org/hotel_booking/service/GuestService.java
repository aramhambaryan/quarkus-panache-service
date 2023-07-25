package org.hotel_booking.service;

import io.smallrye.mutiny.Uni;
import org.hotel_booking.domain.dto.response.GuestFullResponse;
import org.hotel_booking.domain.dto.response.GuestResponse;
import org.hotel_booking.domain.dto.request.GuestRequest;

import java.util.List;

public interface GuestService {

    /**
     * get all guests
     * @return all the guests
     */
    Uni<List<GuestFullResponse>> getAll();

    /**
     * delete the guest with given id
     * @param id id of the guest to be deleted
     * @return true or false
     * @throws javax.ws.rs.NotFoundException if the {@link org.hotel_booking.domain.entity.Booking} with given guest id cannot be found
     */
    Uni<Boolean> deleteById(Long id);

    /**
     * create a new guest with given request
     * @param guestRequest the request to create a new guest from
     * @return the newly created guest
     */
    Uni<GuestResponse> add(GuestRequest guestRequest);

    /**
     * update the guest with given id according to the given request
     * @param id id of the guest to be updated
     * @param guestRequest request to update the guest with
     * @return the updated guest
     */
    Uni<GuestResponse> update(Long id, GuestRequest guestRequest);
}
