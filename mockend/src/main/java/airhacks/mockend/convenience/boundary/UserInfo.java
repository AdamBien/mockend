package airhacks.mockend.convenience.boundary;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import io.quarkus.runtime.Startup;

@Startup
@ApplicationScoped
public class UserInfo {
    

    @PostConstruct
    public void printMessages() {
        System.out.println("use delay-in-ms header to delay all methods");
        System.out.println("use -Ddelay-in-ms=10 to set the delay from the CLI. Is going to be overriden by headers");
    }
}
