package org.hotel_booking.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import org.hotel_booking.domain.entity.Guest;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import java.util.List;

import static org.hotel_booking.util.Utils.NOT_FOUND_MESSAGE;

@ApplicationScoped
public class GuestRepository implements PanacheRepositoryBase<Guest, Long> {

	public Uni<List<Guest>> findAllFetchBookings() {
		return find("select distinct g from Guest g left join fetch g.bookings").list();
	}

	public Uni<Guest> getById(Long id) {
		return findById(id)
				.onItem()
				.ifNull()
				.failWith(() -> new NotFoundException(NOT_FOUND_MESSAGE.apply(
						Guest.class.getSimpleName(),
						Guest.class.getSimpleName().toLowerCase(),
						id)));
	}
}
