package airhacks.mockend.sse.boundary;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

import org.eclipse.microprofile.reactive.messaging.Incoming;

@Path("sse")
@ApplicationScoped
public class SSEResource {

        Sse sse;
        SseBroadcaster broadcaster;

        @GET
        @Produces(MediaType.SERVER_SENT_EVENTS)
        public void register(@Context Sse sse, @Context SseEventSink eventSink) {
            if (this.broadcaster == null){
                this.broadcaster = sse.newBroadcaster();
                this.sse = sse;
            }
            this.broadcaster.register(eventSink);
        }
    
        @Incoming("crud-events")
        public void beat(JsonObject object) {
            if(this.sse == null)
                return;
            OutboundSseEvent outbound = this.sse.newEventBuilder().
                    mediaType(MediaType.APPLICATION_JSON_TYPE).
                    id("" + System.currentTimeMillis()).
                    comment("crud-events").
                    name("crud-event").
                    data(object).
                    build();
            this.broadcaster.broadcast(outbound);
        }   
}
    
