
package airhacks.mockend.statuses.boundary;

import static java.util.Optional.ofNullable;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 *
 * @author airhacks.com
 */
@Path("statuses")
public class StatusesResources {

    private static final String STATUS_PARAM = "status";
    private static final String RESPONSE = "+";

    @GET
    public Response get(@HeaderParam(STATUS_PARAM) @DefaultValue("200") int status) {
        return createResponse(status);
    }

    @POST
    public Response post(String body, @HeaderParam(STATUS_PARAM) @DefaultValue("200") int status) {
        return createResponseWithBody(body, status);
    }

    @PUT
    public Response put(String body, @HeaderParam(STATUS_PARAM) @DefaultValue("200") int status) {
        return createResponseWithBody(body, status);
    }

    @DELETE
    public Response delete(@HeaderParam(STATUS_PARAM) @DefaultValue("200") int status) {
        return createResponse(status);
    }

    @OPTIONS
    public Response options(@HeaderParam(STATUS_PARAM) @DefaultValue("200") int status) {
        return createResponse(status);
    }

    Response createResponse(int status) {
        return Response.
                status(status).
                entity(RESPONSE).
                build();
    }

    Response createResponseWithBody(String body, int status) {
        return Response.
                status(status).
                entity(ofNullable(body).orElse(RESPONSE)).
                build();
    }

}
