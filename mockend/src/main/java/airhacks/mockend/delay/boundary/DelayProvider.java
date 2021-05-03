package airhacks.mockend.delay.boundary;

import java.io.IOException;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Provider
public class DelayProvider implements ContainerRequestFilter {
    
    long delay;

    @Inject
    @ConfigProperty(name = "delay-header-name",defaultValue = "delay-in-ms")
    Instance<String> delayHeaderName;
    
    @Inject
    @ConfigProperty(name="delay-in-ms", defaultValue="0")
    Instance<Long> delayInMs;
    
    public static long convert(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        this.delay = delayInMs.get();
        var headerName = this.delayHeaderName.get();
        System.out.println("requestContext = " + requestContext.getHeaders());
        String delayConfig = requestContext.getHeaderString(headerName);
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
    }
}

