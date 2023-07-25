package org.hotel_booking.service.mapper;

import org.mapstruct.MappingTarget;

import java.util.List;

public interface CommonMapper<E, D1, D2>{
	D1 toDto(E e);

	E toEntity(D2 d);

	E updateEntity(@MappingTarget E e, D2 o);

	List<D1> toDtoList(List<E> entityList);

}
