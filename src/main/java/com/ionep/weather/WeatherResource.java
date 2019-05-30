package com.ionep.weather;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.ionep.weather.http.ForecastService;
import com.ionep.weather.messaging.KafkaForecasters;
import com.ionep.weather.model.Forecast;
import com.ionep.weather.model.ForecastRequest;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class WeatherResource {

    @Inject
    @RestClient
    ForecastService forecaster;


    @POST
    @Path("/http")
    public Forecast http(ForecastRequest request) {
        return forecaster.forecast(request.setRequestId(UUID.randomUUID().toString()));
    }

    @Inject
    KafkaForecasters forecasters;

    @POST
    @Path("/messaging")
    @Produces(MediaType.TEXT_PLAIN)
    public CompletionStage<Response> messaging(ForecastRequest request) {
        System.out.println("Forecast requested for " + request.getCityName());
        request.setRequestId(UUID.randomUUID().toString());
        return forecasters.forecast(request).thenApply(x -> Response.accepted(request.getRequestId()).build());
    }

}
