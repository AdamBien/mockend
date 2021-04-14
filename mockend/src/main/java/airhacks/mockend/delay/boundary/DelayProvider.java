package airhacks.mockend.delay.boundary;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Provider
public class DelayProvider implements ContainerResponseFilter {
    
    @Inject
    @ConfigProperty(name = "delay",defaultValue = "100")
    long delay;

    @Inject
    @ConfigProperty(name = "slowdown-header-name",defaultValue = "slowdown-in-ms")
    String delayHeaderName;
    
    
    public static long convert(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        System.out.println("responseContext = " + requestContext.getHeaders());
        String delayConfig = requestContext.getHeaderString(this.delayHeaderName);
        if (delayConfig != null) {
            this.delay = convert(delayConfig);
        }
        if (delay > 0) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ex) {
                throw new IllegalStateException(ex);
            }
        }
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        headers.putSingle(this.delayHeaderName, delay);

    }
}

