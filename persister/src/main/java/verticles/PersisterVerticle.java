package verticles;

import config.Verticles;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;

import static config.Verticles.*;

public class PersisterVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        EventBus eventBus = vertx.eventBus();
        eventBus.publish(PERSISTER.index(), "Persister is ready");
    }
}
