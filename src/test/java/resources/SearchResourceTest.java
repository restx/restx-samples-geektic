package resources;

import geeks.Geek;
import geeks.Result;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class SearchResourceTest {
  SearchResource searchResource = new SearchResource();

  @Test
  public void should_find_nothing() {
    List<Result> geeks = searchResource.json("");

    assertThat(geeks).isEmpty();
  }

  @Test
  public void should_find_geek_by_like() {
    assertThat(searchResource.json("Smalltalk")).hasSize(1);
    assertThat(searchResource.json("java")).hasSize(18);
  }

    @Test
    public void should_add_geek() throws Exception {
        Geek geek = new Geek();
        geek.likes.add("java");

        int size = searchResource.json("java").size();
        searchResource.addGeek(geek);
        assertThat(searchResource.json("java")).hasSize(size + 1);
    }
}
