package verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;

public class ExposerVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture){
        EventBus eventBus = vertx.eventBus();
        eventBus.publish("[EXPOSER]", "Exposer is ready");
    }
}
