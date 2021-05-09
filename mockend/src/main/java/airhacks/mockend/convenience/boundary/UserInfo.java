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

    @Inject
    @ConfigProperty(name="quarkus.http.port")
    int port;

    Logger LOG = Logger.getLogger(UserInfo.class.getName());
    

    @PostConstruct
    public void printMessages() {
        LOG.info("-----------usage------------");
        LOG.info("swagger-ui: http://localhost:"+this.port+"/q/swagger-ui/");
        LOG.info("create: curl -XPOST \"http://localhost:"+this.port+"/crud\" -H \"Content-Type: application/json\" -d \'{\"message\":\"hello, mockend\"}\'");
        LOG.info("fetch with delay: curl -H\'delay-in-ms:1000\' http://localhost:"+this.port+"/crud/1618928997430");
        LOG.info("echo: curl -X POST \"http://localhost:"+this.port+"/echo\" -H\"Content-Type: text/plain\" -d \"hello, mockend\"");
        LOG.info("status: curl -H\"status: 500\" http://localhost:"+this.port+"/statuses");
        LOG.info("sse: curl -N http://localhost:" + this.port + "/sse");
        LOG.info("You can use: delay-header-name property to change the header name, default is: delay-in-ms");
        LOG.info("use " + this.delayHeaderName + " header to delay all methods");
        LOG.info("use -Ddelay-in-ms=10 to set the delay from the CLI. Is going to be overriden by headers");
        if (this.delayInMs == 0)
            LOG.info("delay is not set or delay-in-ms=0");
        LOG.info("e.g. java -Dquarkus.http.port=8888 -Ddelay-in-ms=10 -jar mockend-runner.jar");
        LOG.info("----------------------------");

    }
}
