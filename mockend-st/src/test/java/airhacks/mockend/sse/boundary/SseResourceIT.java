package airhacks.mockend.sse.boundary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SseResourceIT {

    URI uri;
    HttpClient client;

    @BeforeEach
    public void init() {
        this.uri = URI.create("http://localhost:8080/sse");
        this.client = HttpClient.newHttpClient();
    }

    @Test
    public void receiveEvent() throws IOException, InterruptedException {
        var request = HttpRequest.newBuilder(uri).GET().build();
        var expectedPayload = "expected-" + System.nanoTime();

        var lines = client.send(request, BodyHandlers.ofLines()).body();
        postMessage(expectedPayload);
        var data =lines.filter(line -> line.startsWith("data")).findFirst();
        assertTrue(data.isPresent());
        assertTrue(data.get().contains(expectedPayload));            
        
    }

    
    
    public void postMessage(String message) throws IOException,InterruptedException{
        var input = """
        {
          "message":"%s"
        }
                """.formatted(message);
        var crudUri = URI.create("http://localhost:8080/crud/");
        var request = HttpRequest.newBuilder(crudUri).POST(BodyPublishers.ofString(input))
        .header("Content-type", "application/json").build();
        var response = client.send(request, BodyHandlers.ofString());
        var status = response.statusCode();
        assertEquals(201,status);

    }
}
