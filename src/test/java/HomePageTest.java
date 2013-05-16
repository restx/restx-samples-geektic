import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.fest.assertions.Assertions.assertThat;

public class HomePageTest extends AbstractPageTest {
    @Test
    public void should_display_homepage() throws InterruptedException {
      goTo("/");

      assertThat(title()).isEqualTo("CodeStory - Geektic");

        FluentWebElement searchInput = findFirst("#search input");

        assertThat(searchInput.getText()).isEmpty();
    }

    @Test
    public void should_update_geeks_on_search() throws InterruptedException {
      goTo("/");

        assertThat(find(".resultSquare.shown")).isEmpty();
        fill("#search input").with("test");

        await().atMost(5, TimeUnit.SECONDS).until(".resultSquare.shown").hasSize(1);
    }


    @Test
    public void should_signup_geek() throws InterruptedException {
      goTo("/");

        find("#showSignupBtn").click();

        fill(".signup.form .prenom").with("John");
        fill(".signup.form .nom").with("Doe");
        fill(".signup.form .like1").with("signuptest");

        find(".signup.form .inscription").click();

        await().atMost(5, TimeUnit.SECONDS).until("#intro .user h3").containsText("John Doe");

        fill("#search input").with("signuptest");

        await().atMost(5, TimeUnit.SECONDS).until(".resultSquare.shown").hasSize(1);
    }
}