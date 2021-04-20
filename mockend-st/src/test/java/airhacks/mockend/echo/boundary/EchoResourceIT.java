package airhacks.mockend.echo.boundary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;


import org.junit.jupiter.api.Test;



public class EchoResourceIT {


    @Test
    public void echo() throws IOException, InterruptedException {
        var uri = URI.create("http://localhost:8080/echo");
        var client = HttpClient.newHttpClient();
        var input = "duke";
        var request = HttpRequest.newBuilder(uri).POST(BodyPublishers.ofString(input)).header("Content-type","text/plain").build();
        var response = client.send(request, BodyHandlers.ofString());
        var status = response.statusCode();
        var body = response.body();

        assertEquals(200, status);
        assertTrue(body.endsWith(input));
        assertTrue(body.startsWith("echo:"));


        
    }

    
}
