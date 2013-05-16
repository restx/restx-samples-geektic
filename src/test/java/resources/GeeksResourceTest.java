package resources;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import geeks.Geek;
import geeks.Result;
import main.AppModule;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class GeeksResourceTest {
  GeeksResource geeksResource = new AppModule().searchResource(
          new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false));

  @Test
  public void should_find_nothing() {
    List<Result> geeks = geeksResource.json("");

    assertThat(geeks).isEmpty();
  }

  @Test
  public void should_find_geek_by_like() {
    assertThat(geeksResource.json("Smalltalk")).hasSize(1);
    assertThat(geeksResource.json("java")).hasSize(18);
  }

    @Test
    public void should_add_geek() throws Exception {
        Geek geek = new Geek();
        geek.likes.add("java");

        int size = geeksResource.json("java").size();
        geeksResource.addGeek(geek);
        assertThat(geeksResource.json("java")).hasSize(size + 1);
    }
}
