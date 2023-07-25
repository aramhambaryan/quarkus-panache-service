package org.hotel_booking.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import org.hotel_booking.domain.entity.HotelRoom;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotFoundException;
import java.time.LocalDateTime;

import static org.hotel_booking.util.Utils.NOT_FOUND_MESSAGE;

@ApplicationScoped
public class HotelRoomRepository implements PanacheRepositoryBase<HotelRoom, Long> {

	public Uni<HotelRoom> getById(Long id) {
		return findById(id)
				.onItem()
				.ifNull()
				.failWith(() -> new NotFoundException(NOT_FOUND_MESSAGE.apply(
						HotelRoom.class.getSimpleName(),
						HotelRoom.class.getSimpleName().toLowerCase(),
						id)));
	}

	public Uni<Integer> updateCleaningTimeByRoomNumber(LocalDateTime cleaningTime, int roomNumber) {
		return update("cleaningTime = ?1 where roomNumber = ?2", cleaningTime, roomNumber);
	}
}

