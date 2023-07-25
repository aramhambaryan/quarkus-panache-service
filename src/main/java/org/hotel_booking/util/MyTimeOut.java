package org.hotel_booking.util;

import javax.interceptor.InterceptorBinding;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
@Documented
@InterceptorBinding
public @interface MyTimeOut {
}
