package verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;

public class LoggerVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture){
        EventBus eventBus = vertx.eventBus();
        eventBus.addOutboundInterceptor(message ->
                System.out.println(
                        String.join(
                                " ",
                                message.message().address(),
                                message.message().body().toString()
                        )
                )
        );
    }
}
