package resources;

import restx.*;
import restx.factory.Component;

import java.io.IOException;

@Component
public class MainResource extends StdRoute {
    private final WebResources webResources;

    public MainResource(WebResources webResources) {
        super("main", new StdRouteMatcher("GET", "/"));
        this.webResources = webResources;
    }

    @Override
    public void handle(RestxRouteMatch match, RestxRequest req, RestxResponse resp, RestxContext ctx) throws IOException {
        resp.setContentType("text/html");
        resp.getWriter().print(webResources.templatize(
                webResources.read(
                    webResources.file("index.html").get())));
    }
}
