package verticles;

import io.prometheus.client.vertx.MetricsHandler;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import io.vertx.ext.web.Router;
import service.StockMetricsService;

import static config.Verticles.EXPOSER;
import static config.Verticles.SCRAPER;

public class ExposerVerticle extends AbstractVerticle {
    private StockMetricsService stockMetricsService = new StockMetricsService();

    @Override
    public void start(Future<Void> startFuture) {
        EventBus eventBus = vertx.eventBus();
        final Router router = Router.router(vertx);
        router.route("/metrics").handler(new MetricsHandler());
        vertx.createHttpServer().requestHandler(router).listen(8080);
        eventBus.publish(EXPOSER.index(), "Exposer is ready");
        eventBus.consumer(SCRAPER.index(), stockMetricsService::handle);
    }

}
