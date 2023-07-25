package org.hotel_booking.util;

import io.quarkus.arc.Priority;
import io.smallrye.mutiny.Uni;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.time.Duration;
import java.util.concurrent.TimeoutException;

@MyTimeOut
@Interceptor
// если не добавлять будет присвоен дефолтный приоритет 0
//@Priority(1)
public class MyTimeOutInterceptor {

	@AroundInvoke
	public Object aroundInvoke(InvocationContext context) throws Exception {
		Object r = context.proceed();
		if (r instanceof Uni) {
			return ((Uni<?>) r)
					.ifNoItem()
					// если тут цифра будет меньше, чем ты выставил задержку на другом сервисе, то при попытке запроса, все будет падать в ошибку
					// на текущий момент я сделал задержку в 5 секунд на сервисе cleanings, а тут 3, значит всегда будет падать в ошибку
					.after(Duration.ofSeconds(3))
					.failWith(() -> new TimeoutException("gg wp ff 20"));
					// можно добавлять попытки ретрая если вдруг надо
//					.onFailure().retry().atMost(1);
		}
		return r;

	}
}
