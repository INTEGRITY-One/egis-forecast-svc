package com.ionep.weather.http;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.ionep.weather.model.Forecast;
import com.ionep.weather.model.ForecastRequest;

@Path("/forecaster")
@RegisterRestClient
public interface ForecastService {

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    Forecast forecast(ForecastRequest request);
}
