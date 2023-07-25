package org.hotel_booking.util;

import io.smallrye.mutiny.tuples.Functions;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {
	public static final Functions.Function3<String, String, Long, String> NOT_FOUND_MESSAGE = (name, searchBy, id) ->
			String.format("%s with %s.id %d not found", name, searchBy, id);
}
