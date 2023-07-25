package org.hotel_booking.service.impl;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.hotel_booking.domain.dto.response.CleaningResponse;
import org.hotel_booking.repository.HotelRoomRepository;
import org.hotel_booking.service.client.CleaningRestClient;

import javax.enterprise.context.ApplicationScoped;
import java.util.stream.Collectors;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class CleaningUpdaterService {

    @RestClient
    CleaningRestClient cleaningRestClient;
    private final HotelRoomRepository hotelRoomRepository;

    @Scheduled(every = "${auto.update.cleaning:off}")
    @ReactiveTransactional
    public Uni<Void> updateCleanings() {
        return Multi.createFrom().range(1, 4)
                .onItem().transformToMultiAndMerge(this::updateCleanings)
                .onFailure().invoke(ex -> log.error(ex.getMessage()))
                .collect().with(Collectors.toList())
                .replaceWithVoid();
    }

    private Multi<CleaningResponse> updateCleanings(int part) {
        return cleaningRestClient.getPartData(part)
                .onItem().transformToMulti(Multi.createFrom()::iterable)
                .call(cleaningResponse -> hotelRoomRepository.updateCleaningTimeByRoomNumber(
                        cleaningResponse.getCleaningTime(),
                        cleaningResponse.getRoomNumber()));
    }
}
