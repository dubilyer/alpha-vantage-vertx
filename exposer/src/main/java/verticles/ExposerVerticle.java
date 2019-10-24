package verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;

public class ExposerVerticle extends AbstractVerticle {
    EventBus eventBus;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        eventBus = vertx.eventBus();
        eventBus.publish("[EXPOSER]", "Exposer is ready");
    }
}
