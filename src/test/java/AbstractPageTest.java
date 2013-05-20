import misc.PhantomJsTest;
import org.junit.ClassRule;
import restx.server.WebServer;
import restx.server.WebServerSupplier;
import restx.server.simple.simple.SimpleWebServer;
import restx.tests.RestxSpecRule;

public abstract class AbstractPageTest extends PhantomJsTest {
    @ClassRule
    public static RestxSpecRule rule = new RestxSpecRule("", new WebServerSupplier() {
        @Override
        public WebServer newWebServer(int port) {
            return new SimpleWebServer("", "", port);
        }
    }, RestxSpecRule.defaultFactory());

    @Override
    protected String defaultUrl() {
        return rule.getServer().baseUrl();
    }
}