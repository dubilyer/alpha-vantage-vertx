package verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import service.StockService;

public class ScraperVerticle extends AbstractVerticle {
    private static final String PREFIX = "[SCRAPER]";

    @Override
    public void start(Future<Void> startFuture) {
        EventBus eventBus = vertx.eventBus();
        StockService stockService = new StockService(vertx);
        stockService.init();
        eventBus.publish(PREFIX, "Scraper is ready");
        stockService.query("TIME_SERIES_INTRADAY", "MSFT", "1min");
    }
}
