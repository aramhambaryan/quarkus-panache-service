package org.hotel_booking.domain.dto.request;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class GuestRequest {

    @NotBlank
    String firstName;
    @NotBlank
    String lastName;
    @NotBlank
    String middleName;
    @NotNull
    LocalDate birthdayDate;
}
