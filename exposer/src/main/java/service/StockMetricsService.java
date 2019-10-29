package service;

import exceptions.ExposerException;
import io.prometheus.client.Gauge;
import io.vertx.core.eventbus.Message;
import model.stock.Label;
import model.stock.StockResponse;
import model.stock.Value;

import java.util.HashMap;
import java.util.Map;

import static model.stock.Label.CLOSE;

public class StockMetricsService {

    private Map<String, Gauge> metrics = new HashMap<>();

    public void updateMetrics(Message<StockResponse> message){
        updateMetrics(message.body());
    }

    private void updateMetrics(StockResponse stockResponse){
        updateMetrics(
                stockResponse.getMetadata().get("2. Symbol"),
                stockResponse
                        .getTimeSeries()
                        .entrySet()
                        .stream()
                        .findFirst()
                        .orElseThrow(() -> new ExposerException(String.format("No timeseries found! %s", stockResponse.toString())))
                        .getValue()
        );
    }

    private void updateMetrics(String key, Value value) {
        metrics
                .computeIfAbsent(key, k -> Gauge
                        .build()
                        .name(k)
                        .help(k)
//                        .labelNames(Arrays.stream(Label.values()).map(Enum::name).toArray(String[]::new))
                        .register());
        setMetric(key, CLOSE, value.getClose());
//        setMetric(key, HIGH, value.getHigh());
//        setMetric(key, LOW, value.getLow());
//        setMetric(key, OPEN, value.getOpen());
//        setMetric(key, VOLUME, value.getVolume());
    }

    private void setMetric(String key, Label label, String value) {
        metrics.get(key)/*.labels(label.name())*/.set(Double.parseDouble(value));
    }
}
