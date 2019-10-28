package verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import repository.InfluxRepo;

import static config.Verticles.PERSISTER;
import static config.Verticles.SCRAPER;

public class PersisterVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) {
        EventBus eventBus = vertx.eventBus();
        InfluxRepo influxRepo = new InfluxRepo(eventBus);
        eventBus.publish(PERSISTER.index(), "Persister is ready");
        eventBus.consumer(SCRAPER.index(), influxRepo.stockPersistHandler);
    }

}
