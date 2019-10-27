package verticles;

import config.Verticles;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import model.StockResponseCodec;
import service.StockService;
import worker.Worker;

import java.util.LinkedList;
import java.util.List;

import static config.Verticles.*;

public class ScraperVerticle extends AbstractVerticle {
    private List<Worker> workers;

    @Override
    public void start(Future<Void> startFuture) {
        EventBus eventBus = vertx.eventBus();
        initWorkers();
        eventBus.publish(SCRAPER.index(), "Scraper is ready");
        eventBus.registerCodec(new StockResponseCodec());
        workers.forEach(w -> w.schedule(vertx));
    }

    private void initWorkers() {
        StockService stockService = new StockService(vertx);
        stockService.init();
        workers = new LinkedList<>();
        workers.add(new Worker(l -> stockService.query("TIME_SERIES_INTRADAY", "MSFT", "1min"), 5000));
    }
}
