package verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import model.stock.StockResponse;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import static config.Verticles.PERSISTER;
import static config.Verticles.SCRAPER;

public class PersisterVerticle extends AbstractVerticle {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        EventBus eventBus = vertx.eventBus();
        InfluxDB influx = InfluxDBFactory.connect("http://192.168.99.100:8086");
        String dbName = "metrics";
        influx.setDatabase(dbName);
//        influx.query(new Query("CREATE DATABASE " + dbName + ';'));
        eventBus.publish(PERSISTER.index(), "Persister is ready");
        eventBus.<StockResponse>consumer(
                SCRAPER.index(), message -> {
                    try {
                        message
                                .body()
                                .getTimeSeries()
                                .forEach(
                                        (key, value) -> influx.write(
                                                Point
                                                        .measurement(message.body().getMetadata().get("2. Symbol"))
                                                        .time(
                                                                LocalDateTime
                                                                        .parse(key, FORMATTER)
                                                                        .toEpochSecond(ZoneOffset.UTC),
                                                                TimeUnit.SECONDS)
                                                        .addField("open", value.getOpen())
                                                        .addField("high", value.getHigh())
                                                        .addField("low", value.getLow())
                                                        .addField("close", value.getClose())
                                                        .addField("volume", value.getVolume())
                                                        .build()));
                    } catch (ClassCastException e){
                        eventBus.publish(PERSISTER.index(), "message is not of class StockResponse");
                    }
                });
    }
}
