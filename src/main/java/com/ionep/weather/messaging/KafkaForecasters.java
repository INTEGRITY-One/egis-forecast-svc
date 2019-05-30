package com.ionep.weather.messaging;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.Jsonb;

import com.ionep.weather.model.ForecastRequest;
import com.ionep.weather.model.PreparationState;

import io.smallrye.reactive.messaging.annotations.Emitter;
import io.smallrye.reactive.messaging.annotations.Stream;

@ApplicationScoped
public class KafkaForecasters {

    @Inject
    @Stream("requests")
    Emitter<String> requests;

    @Inject
    @Stream("queue")
    Emitter<String> forecasts;

    @Inject
    Jsonb jsonb;

    private Executor executor = Executors.newSingleThreadExecutor();

    public CompletionStage<ForecastRequest> forecast(ForecastRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            forecasts.send(PreparationState.queued(request));
            requests.send(jsonb.toJson(request));
            System.out.println("Forecasting for " + request.getCityName());
            return request;
        }, executor);
    }

}
