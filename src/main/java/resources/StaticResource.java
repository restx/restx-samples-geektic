package resources;

import com.google.common.base.Optional;
import com.google.common.io.Files;
import restx.*;
import restx.factory.Component;
import restx.server.HTTP;
import webserver.compilers.CoffeeScriptCompiler;
import webserver.compilers.LessCompiler;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class StaticResource extends StdRoute {

    private final CoffeeScriptCompiler coffeeScriptCompiler;
    private final LessCompiler lessCompiler;
    private final WebResources webResources;

    @Inject
    public StaticResource(CoffeeScriptCompiler coffeeScriptCompiler, LessCompiler lessCompiler, final WebResources webResources) {
        super("static", new RestxRouteMatcher() {
            @Override
            public Optional<RestxRouteMatch> match(RestxHandler handler, String method, String path) {
                if (path.startsWith("/static/")) {
                    return Optional.of(new RestxRouteMatch(handler, path));
                } else {
                    return Optional.absent();
                }
            }
        });
        this.coffeeScriptCompiler = coffeeScriptCompiler;
        this.lessCompiler = lessCompiler;
        this.webResources = webResources;
    }

    @Override
    public void handle(RestxRouteMatch match, RestxRequest req, RestxResponse resp, RestxContext ctx) throws IOException {
        String path = match.getPath();
        Matcher matcher = Pattern.compile("/static/[^/]+/(.*)").matcher(path);
        if (!matcher.matches()) {
            notFound(match, resp);
            return;
        }

        if (path.endsWith(".coffee") || path.endsWith(".less")) {
            Optional<File> file = webResources.file(matcher.group(1));
            if (!file.isPresent()) {
                notFound(match, resp);
                return;
            }

            if (path.endsWith(".coffee")) {
                resp.setContentType("application/javascript;charset=UTF-8");
                resp.getWriter().print(coffeeScriptCompiler.compile(file.get()));
                return;
            }
            if (path.endsWith(".less")) {
                resp.setContentType("text/css;charset=UTF-8");
                resp.getWriter().print(lessCompiler.compile(file.get()));
                return;
            }
        }

        Optional<File> file = webResources.file("static", matcher.group(1));
        if (!file.isPresent()) {
            notFound(match, resp);
            return;
        }
        resp.setContentType(HTTP.getContentTypeFromExtension(path).or("application/octet-stream"));
        Files.copy(file.get(), resp.getOutputStream());
    }
}
