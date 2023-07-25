package org.hotel_booking.service.impl;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.helpers.test.UniAssertSubscriber;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.api.Assertions;
import org.hotel_booking.domain.dto.request.GuestRequest;
import org.hotel_booking.domain.dto.response.GuestFullResponse;
import org.hotel_booking.domain.entity.Booking;
import org.hotel_booking.domain.entity.Guest;
import org.hotel_booking.domain.entity.HotelRoom;
import org.hotel_booking.domain.enums.HotelRoomCategory;
import org.hotel_booking.repository.BookingRepository;
import org.hotel_booking.repository.GuestRepository;
import org.hotel_booking.service.mapper.GuestMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.NotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuestServiceImplTest {

    @InjectMocks
    private GuestServiceImpl guestService;
    @Mock
    private GuestRepository guestRepository;
    @Spy
    private GuestMapper guestMapper = Mappers.getMapper(GuestMapper.class);
    @Mock
    private BookingRepository bookingRepository;
    private static final List<HotelRoom> hotelRooms = roomsWithRandomCategory();

    @Nested
    class GetAll {

        @Test
        void getAll_Success() {
            List<Guest> guests = randomGuestsWithRandomBookings();
            Uni<List<Guest>> uniGuests = Uni.createFrom().item(guests);
            List<GuestFullResponse> guestsDtos = guestMapper.toFullDtoList(guests);

            when(guestRepository.findAllFetchBookings()).thenReturn(uniGuests);
            guestService.getAll()
                    .subscribe().withSubscriber(UniAssertSubscriber.create())
                    .assertCompleted()
                    .assertItem(guestsDtos);

            verify(guestMapper, times(2)).toFullDtoList(guests);
            verify(guestRepository).findAllFetchBookings();
        }
    }

    @Nested
    class DeleteById {
        @ParameterizedTest
        @MethodSource("org.hotel_booking.service.impl.GuestServiceImplTest#randomLongStream")
        void delete_success(Long id) {
            // можно еще так делать
            when(bookingRepository.deleteByGuestId(anyLong())).thenReturn(Uni.createFrom().item(4L));
            when(guestRepository.deleteById(anyLong())).thenReturn(Uni.createFrom().item(true));

            guestService.deleteById(id)
                    .subscribe()
                    .withSubscriber(UniAssertSubscriber.create())
                    .assertCompleted()
                    .assertItem(true);

            verify(bookingRepository).deleteByGuestId(id);
            verify(guestRepository).deleteById(id);
        }

        @ParameterizedTest
        @MethodSource("org.hotel_booking.service.impl.GuestServiceImplTest#randomLongStream")
        void delete_NotFoundException(Long id) {
            when(bookingRepository.deleteByGuestId(id)).thenReturn(Uni.createFrom().nullItem());

            guestService.deleteById(id)
                    .subscribe()
                    .withSubscriber(UniAssertSubscriber.create())
                    .assertFailedWith(NotFoundException.class, "Booking with guest.id " + id +  " not found");

            verify(bookingRepository).deleteByGuestId(id);
            verify(guestRepository, never()).deleteById(any());
        }
    }

    @Nested
    class Add {

        @Test
        void add_success() {
            GuestRequest inputGuestReq = randomGuestRequest();
            Guest guest = new Guest();
            guest.setLastName(inputGuestReq.getLastName());
            guest.setMiddleName(inputGuestReq.getMiddleName());
            guest.setBirthdayDate(inputGuestReq.getBirthdayDate());
            guest.setFirstName(inputGuestReq.getFirstName());
            Uni<Guest> guestUni = Uni.createFrom().item(guest);

            when(guestRepository.persistAndFlush(any())).thenReturn(guestUni);

            // captor нужен, чтобы проверять, что попадает внутрь войд методов, сначала ты получаешь значение через captor.capture(), потом в ассертах проверяешь его через captor.getValue()
            ArgumentCaptor<GuestRequest> reqCaptor = forClass(GuestRequest.class);

            guestService.add(inputGuestReq).subscribe().withSubscriber(UniAssertSubscriber.create())
                    .assertCompleted();

            verify(guestRepository).persistAndFlush(guest);
            verify(guestMapper, times(1)).toEntity(reqCaptor.capture());
            verify(guestMapper).toDto(guest);

            Assertions.assertThat(reqCaptor.getValue()).isEqualTo(inputGuestReq);

        }
    }

    @Nested
    class Update {

        @ParameterizedTest
        @MethodSource("org.hotel_booking.service.impl.GuestServiceImplTest#randomLongStream")
        void update_success(long id) {
            GuestRequest guestRequest = randomGuestRequest();
            Guest guest = guestMapper.toEntity(guestRequest);
            Uni<Guest> uniGuest = Uni.createFrom().item(guest);
            guest.setId(id);

            when(guestRepository.getById(id)).thenReturn(uniGuest);

            guestService.update(id, guestRequest)
                    .subscribe().withSubscriber(UniAssertSubscriber.create())
                    .assertCompleted();

            verify(guestRepository).getById(id);
            verify(guestMapper).updateEntity(guest, guestRequest);

        }

        @ParameterizedTest
        @MethodSource("org.hotel_booking.service.impl.GuestServiceImplTest#randomRequestStream")
        void update_fail(GuestRequest guestRequest) {
            long id = 5L;
            when(guestRepository.getById(id)).thenReturn(Uni.createFrom().failure(NotFoundException::new));

            guestService.update(id, guestRequest)
                    .subscribe().withSubscriber(UniAssertSubscriber.create())
                    .assertFailedWith(NotFoundException.class);

            verify(guestRepository).getById(id);
            verify(guestMapper, never()).updateEntity(any(), any());

        }
    }

    static private Stream<Long> randomLongStream() {
        return Stream.generate(RandomUtils::nextLong)
                .limit(10);
    }

    static private Stream<GuestRequest> randomRequestStream() {
        return Stream.generate(GuestServiceImplTest::randomGuestRequest)
                .limit(10);
    }


    static private GuestRequest randomGuestRequest() {
        GuestRequest gr = new GuestRequest();
        gr.setFirstName(RandomStringUtils.randomAlphabetic(10));
        gr.setLastName(RandomStringUtils.randomAlphabetic(10));
        gr.setMiddleName(RandomStringUtils.randomAlphabetic(10));
        gr.setBirthdayDate(LocalDate.now().minusYears(RandomUtils.nextLong(20, 40)));
        return gr;
    }

    static private List<Guest> randomGuestsWithRandomBookings() {
        return IntStream.range(1, 21).mapToObj(l -> {
            Guest guest = randomGuest();
            Set<Booking> bookings = IntStream.range(0, 4).mapToObj(i -> randomBooking(guest)).collect(Collectors.toSet());
            guest.setBookings(bookings);
            return guest;
        }).collect(Collectors.toList());
    }

    static private Guest randomGuest() {
        Guest guest = new Guest();
        guest.setId(RandomUtils.nextLong());
        guest.setFirstName(RandomStringUtils.randomAlphabetic(10));
        guest.setLastName(RandomStringUtils.randomAlphabetic(10));
        guest.setMiddleName(RandomStringUtils.randomAlphabetic(10));
        guest.setBirthdayDate(LocalDate.now().minusYears(RandomUtils.nextLong(20, 50)));
        return guest;
    }

    static private Booking randomBooking(Guest guest) {
        Booking booking = new Booking();
        booking.setId(RandomUtils.nextLong());
        booking.setCheckInDate(LocalDate.now().minusDays(RandomUtils.nextLong(10, 100)));
        booking.setCheckOutDate(booking.getCheckInDate().plusDays(5));
        booking.setHotelRoom(hotelRooms.get(RandomUtils.nextInt(0, hotelRooms.size())));
        booking.setGuest(guest);
        return booking;
    }

    static List<HotelRoom> roomsWithRandomCategory() {
        return LongStream.range(1, 11).mapToObj(l -> {
            HotelRoom hr = new HotelRoom();
            hr.setId(l);
            hr.setRoomNumber((int)l);
            int i = RandomUtils.nextInt(0, HotelRoomCategory.values().length);
            hr.setCategory(HotelRoomCategory.values()[i]);
            return hr;
        }).collect(Collectors.toList());
    }
}