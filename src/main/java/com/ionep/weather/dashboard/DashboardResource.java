package com.ionep.weather.dashboard;

import io.smallrye.reactive.messaging.annotations.Stream;
import org.eclipse.microprofile.reactive.streams.operators.PublisherBuilder;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/queue")
public class DashboardResource {

    @Inject
    @Stream("forecasts")
    PublisherBuilder<String> queue;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Publisher<String> getQueue() {
        return queue.peek(s -> System.out.println("GOT: " + s)).buildRs();
    }

}
