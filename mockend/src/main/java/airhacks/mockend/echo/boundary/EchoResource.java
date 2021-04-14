package airhacks.mockend.echo.boundary;

import javax.ws.rs.Consumes;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("echo")
public class EchoResource {

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String hello(String input) {
        return "echo:" + input;
    }
}