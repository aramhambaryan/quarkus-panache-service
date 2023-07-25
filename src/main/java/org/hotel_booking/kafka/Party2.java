package org.hotel_booking.kafka;

import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.annotations.Broadcast;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.hotel_booking.domain.dto.kafka.BookingNotification;
import org.hotel_booking.domain.dto.kafka.GuestNotification;
import org.hotel_booking.domain.dto.kafka.KafkaNotification;
import org.hotel_booking.repository.GuestRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.control.ActivateRequestContext;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
public class Party2 {

    private final GuestRepository guestRepository;

//    забираем из брокера
    @Incoming("some-channel-in")
//     это уже идет в in memory
    @Outgoing("some-channel-in-memory")
//     позволяет отправлять сразу на несколько консьюмеров
    @Broadcast
    @ActivateRequestContext
    public Uni<String> consumeAndProduce(Message<KafkaNotification> msg) {
        return guestRepository.findAll().count()
                .invoke(count -> log(count, msg.getPayload()))
                .flatMap(x -> Uni.createFrom().completionStage(msg.ack())
                        .replaceWith("message cycle completed"))
                .onFailure().call(th -> Uni.createFrom().completionStage(msg.nack(th))
                        .invoke(() -> log.error("message nacked", th)));
    }

    private void log(long guestCount, KafkaNotification kn) {
        GuestNotification gn = kn.getGuestNotification();
        BookingNotification bn = kn.getBookingNotification();

        String guestInfo = String.format("guest : %s %s %s", gn.getFirstName(), gn.getMiddleName(), gn.getLastName());
        String bookingInfo = String.format("room id : %s%n" + "check in date : %s%n" + "checkout date : %s",
                bn.getHotelRoomId(), bn.getCheckInDate(), bn.getCheckOutDate());
        String guestCountInfo = String.format("Currently there are '%s' guests at the hotel%n", guestCount);

        String logMessage = String.format("New booking :%n%s%n%s%n%s%n", guestInfo, bookingInfo, guestCountInfo);
        log.info(logMessage);
    }

}
