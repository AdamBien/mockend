package airhacks.mockend.crud.boundary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonArray;
import javax.json.JsonObject;
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

@Path("crud")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CRUDResource {

    Map<String, JsonObject> store;
    
    @PostConstruct
    public void init() {
        this.store = new HashMap<>();
    }

    @GET
    @Path("{id}")
    public JsonObject find(@PathParam("id") String id) {
        return this.store.get(id);
    }

    @GET
    public JsonArray findAll() {
        return this.store.values().stream().collect(JsonCollectors.toJsonArray());
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") String id) {
        this.store.get(id);
    }

    @PUT
    @Path("{id}")
    public void upsert(@PathParam("id") String id, JsonObject input) {
        this.store.put(id, input);
    }
    
    @POST
    public void insert(JsonObject input) {
        var generatedId = "" + System.currentTimeMillis();
        this.store.put(generatedId, input);
    }

}