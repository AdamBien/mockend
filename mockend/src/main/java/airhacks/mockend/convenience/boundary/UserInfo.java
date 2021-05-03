package airhacks.mockend.convenience.boundary;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.runtime.Startup;

@Startup
@ApplicationScoped
public class UserInfo {

    @Inject
    @ConfigProperty(name = "delay-header-name",defaultValue = "delay-in-ms")
    String delayHeaderName;
    
    @Inject
    @ConfigProperty(name="delay-in-ms", defaultValue="0")
    Long delayInMs;

    Logger LOG = Logger.getLogger(UserInfo.class.getName());
    

    @PostConstruct
    public void printMessages() {
        LOG.info("----------------------------");
        LOG.info("You can use: delay-header-name property to change the header name, default is: delay-in-ms");
        LOG.info("use " + this.delayHeaderName + " header to delay all methods");
        LOG.info("use -Ddelay-in-ms=10 to set the delay from the CLI. Is going to be overriden by headers");
        if (this.delayInMs == 0)
            LOG.info("delay is not set or delay-in-ms=0");
        LOG.info("e.g. java -jar -Ddelay-in-ms=10 -jar mockend-runner.jar");
        LOG.info("----------------------------");

    }
}
