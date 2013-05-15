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
}