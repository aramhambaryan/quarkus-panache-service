package org.hotel_booking.kafka;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.hotel_booking.domain.dto.kafka.BookingNotification;
import org.hotel_booking.domain.dto.kafka.GuestNotification;
import org.hotel_booking.domain.dto.kafka.KafkaNotification;
import org.hotel_booking.domain.dto.response.BookingFullResponse;
import org.hotel_booking.service.mapper.BookingMapper;
import org.hotel_booking.service.mapper.GuestMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Slf4j
@RequiredArgsConstructor
@ApplicationScoped
public class Party1 {

    @Inject
    @Channel("some-channel-out")
    MutinyEmitter<KafkaNotification> emitter;

    private final GuestMapper guestMapper;
    private final BookingMapper bookingMapper;


    // отправляем в брокер в топик shared_topic
    public Uni<Void> sendBooking(BookingFullResponse bfr) {
        return emitter.send(toKafkaNotification(bfr))
                .invoke(() -> log.info("sending message to some-channel-in Party2 class"))
                .onFailure().invoke(() -> log.info("Message nacked by broker"));
    }



    @Incoming("some-channel-in-memory")
    public void consumer1(String message) {
        log.info("Consumer 1 received message from party2 : " + message);
    }

    @Incoming("some-channel-in-memory")
    public void consumer2(String message) {
        log.info("Consumer 2 received message from party2 : " + message);
    }

    private KafkaNotification toKafkaNotification(BookingFullResponse bfr) {
        GuestNotification gn = guestMapper.toGuestNotification(bfr.getGuest());
        BookingNotification bn = bookingMapper.toBookingNotification(bfr);
        return KafkaNotification.builder()
                .guestNotification(gn)
                .bookingNotification(bn)
                .build();

    }
}
