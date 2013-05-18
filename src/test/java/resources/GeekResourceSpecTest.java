package resources;

import org.junit.ClassRule;
import org.junit.Test;
import restx.server.WebServer;
import restx.server.WebServerSupplier;
import restx.server.simple.simple.SimpleWebServer;
import restx.tests.RestxSpecRule;

/**
 * User: xavierhanin
 * Date: 5/18/13
 * Time: 11:50 AM
 */
public class GeekResourceSpecTest {
    @ClassRule
    public static RestxSpecRule rule = new RestxSpecRule("", new WebServerSupplier() {
        @Override
        public WebServer newWebServer(int port) {
            return new SimpleWebServer("", "", port);
        }
    }, RestxSpecRule.defaultFactory());


    @Test
    public void should_add_a_geek() throws Exception {
        rule.runTest("specs/geeks/should_add_a_geek.spec.yaml");
    }

    @Test
    public void should_return_no_geek_when_no_match() throws Exception {
        rule.runTest("specs/geeks/should_return_no_geek_when_no_match.spec.yaml");
    }

    @Test
    public void should_return_one_geek_on_a_match() throws Exception {
        rule.runTest("specs/geeks/should_return_one_geek_on_a_match.spec.yaml");
    }

}
