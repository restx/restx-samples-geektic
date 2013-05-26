import misc.PhantomJsTest;
import org.junit.ClassRule;
import restx.server.WebServer;
import restx.server.WebServerSupplier;
import restx.server.simple.simple.SimpleWebServer;
import restx.tests.RestxSpecRule;

public abstract class AbstractPageTest extends PhantomJsTest {
    @ClassRule
    public static RestxSpecRule rule = (RestxSpecRule) new RestxSpecRule("", new WebServerSupplier() {
        @Override
        public WebServer newWebServer(int port) {
            return SimpleWebServer.builder().setRouterPath("").setPort(port).build();
        }
    }, RestxSpecRule.defaultFactory()).setFactoryLoadMode("onstartup");

    @Override
    protected String defaultUrl() {
        return rule.getServer().baseUrl();
    }
}