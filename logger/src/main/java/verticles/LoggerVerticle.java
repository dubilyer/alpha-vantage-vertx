package verticles;

import config.Verticles;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;

import java.util.stream.Stream;

import static config.Verticles.*;

public class LoggerVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) {
        EventBus eventBus = vertx.eventBus();
        eventBus.publish(LOGGER.index(), "Logger is ready");
        System.out.println(LOGGER.index() + " Logger is ready");
        Stream.of(EXPOSER, PERSISTER, SCRAPER).forEach(
                id -> eventBus.consumer(id.index(), message ->
                        System.out.println(
                                String.join(
                                        " ",
                                        message.address(),
                                        message.body().toString()
                                )
                        )
                )
        );
    }
}