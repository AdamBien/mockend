package airhacks.mockend.crud.boundary;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.stream.JsonCollectors;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;



@Path("crud")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CRUDResource {

    @Inject
    @Channel("crud-events")
    Emitter<JsonObject> emitter;

    Map<String, JsonObject> store;
    
    @PostConstruct
    public void init() {
        this.store = new HashMap<>();
    }

    @GET
    @Path("{id}")
    public JsonObject find(@PathParam("id") String id) {
        var result = this.store.get(id);
        sendEvent("GET","/"+id, result);
        return result;
    }

    @GET
    public JsonArray findAll() {
        var result = this.store.values().stream().collect(JsonCollectors.toJsonArray());
        sendEvent("GET", "/",result);
        return result;
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") String id) {
        sendEvent("DELETE", "/"+id);
        this.store.remove(id);
    }

    @DELETE
    public void deleteAll() {
        sendEvent("DELETE", "/");
        this.store.clear();
    }

    @PUT
    @Path("{id}")
    public Response upsert(@PathParam("id") String id, JsonObject input) {
        var payload = addId(input, id);
        var previous = this.store.put(id, payload);
        sendEvent("POST", "/", payload);

        return previous == null ? Response.created(URI.create("/crud/"+id)).build() : Response.noContent().build();

    }
    
    @POST
    public Response insert(JsonObject input) {
        var generatedId = "" + System.currentTimeMillis();
        var payload = addId(input, generatedId);
        this.store.put(generatedId, payload);
        sendEvent("POST", "/", payload);
        return Response.created(URI.create("/crud/" + generatedId)).build();
    }

    JsonObject addId(JsonObject input, String id) {
        return Json.createObjectBuilder(input).add("id", id).build();
    }
    
    void sendEvent(String httpMethod, String path, JsonObject payload) {
        var event = createEvent(httpMethod, path, payload);
        emitter.send(event);
    }

    void sendEvent(String httpMethod, String path, JsonArray payload) {
        var event = createEvent(httpMethod, path, payload);
        emitter.send(event);
    }

    void sendEvent(String httpMethod, String path) {
        var event = createEvent(httpMethod, path);
        emitter.send(event);
    }

    JsonObject createEvent(String httpMethod, String path, JsonObject payload) {
        var event = createEvent(httpMethod,path);
        return Json.createObjectBuilder(event).add("payload", payload)
                .build();
    }
    
    JsonObject createEvent(String httpMethod, String path) {
        return Json.createObjectBuilder().add("httpMethod", httpMethod).add("path", path).build();
    }
    
    JsonObject createEvent(String httpMethod, String path, JsonArray payload) {
        var wrapper = Json.createObjectBuilder().add("result", payload).build();
        return this.createEvent(httpMethod, path, wrapper);
    }

}