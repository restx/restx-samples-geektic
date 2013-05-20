import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Test;
import restx.specs.RestxSpec;
import restx.specs.mongo.GivenJongoCollection;
import restx.tests.WhenThenCheck;

import java.util.concurrent.TimeUnit;

import static org.fest.assertions.Assertions.assertThat;

public class HomePageTest extends AbstractPageTest {
    @Test
    public void should_display_homepage() throws InterruptedException {
        runTestWithGeeks("{ \"_id\" : { \"$oid\" : \"519526f5207108cdfa4d0cc0\"} , " +
                "\"nom\" : \"Doe\" , \"prenom\" : \"John\" , " +
                "\"email\" : \"xav@acme.com\" , \"likes\" : [ \"test\"]}",
                new WhenThenCheck() {
                    @Override
                    protected void check(ImmutableMap<String, String> params) {
                        goTo("/");

                        assertThat(title()).isEqualTo("CodeStory - Geektic");

                        FluentWebElement searchInput = findFirst("#search input");

                        assertThat(searchInput.getText()).isEmpty();
                    }
                });
    }

    @Test
    public void should_update_geeks_on_search() throws InterruptedException {
        runTestWithGeeks("{ \"_id\" : { \"$oid\" : \"519526f5207108cdfa4d0cc0\"} , " +
                        "\"nom\" : \"Doe\" , \"prenom\" : \"John\" , " +
                        "\"email\" : \"xav@acme.com\" , \"likes\" : [ \"test\"]}",
                new WhenThenCheck() {
                    @Override
                    protected void check(ImmutableMap<String, String> params) {
                        goTo("/");

                        assertThat(find(".resultSquare.shown")).isEmpty();
                        fill("#search input").with("test");

                        await().atMost(5, TimeUnit.SECONDS).until(".resultSquare.shown").hasSize(1);
                    }
                });
    }

    @Test
    public void should_signup_geek() throws InterruptedException {
        runTestWithGeeks("{ \"_id\" : { \"$oid\" : \"519526f5207108cdfa4d0cc0\"} , " +
                        "\"nom\" : \"Doe\" , \"prenom\" : \"John\" , " +
                        "\"email\" : \"xav@acme.com\" , \"likes\" : [ \"test\"]}",
                new WhenThenCheck() {
                    @Override
                    protected void check(ImmutableMap<String, String> params) {
                        goTo("/");

                        find("#showSignupBtn").click();

                        fill(".signup.form .prenom").with("John");
                        fill(".signup.form .nom").with("Doe");
                        fill(".signup.form .email").with("test@acme.com");
                        fill(".signup.form .like1").with("signuptest");

                        find(".signup.form .inscription").click();

                        await().atMost(5, TimeUnit.SECONDS).until("#intro .user h3").containsText("John Doe");

                        fill("#search input").with("signuptest");

                        await().atMost(5, TimeUnit.SECONDS).until(".resultSquare.shown").hasSize(1);

                        assertThat(find(".resultSquare.shown img").first().getAttribute("src")).isEqualTo("http://gravatar.com/avatar/cd39cb289b96854268bbdd63908230ab?s=256");
                    }
                });
    }


    private void runTestWithGeeks(String geeks, WhenThenCheck check) {
        rule.runTest(new RestxSpec("test",
                ImmutableList.<RestxSpec.Given>of(new GivenJongoCollection("geeks", "",
                        geeks,
                        ImmutableList.<String>of())),
                ImmutableList.<RestxSpec.When>of(check)
        ));
    }
}