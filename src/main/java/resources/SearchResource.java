package resources;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import geeks.Geek;
import geeks.Result;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;

@Singleton
@Path("/search")
public class SearchResource extends AbstractResource {
  private List<Geek> geeks;

  public SearchResource() {
    geeks = Lists.newArrayList(new Gson().<Geek[]>fromJson(read(file("geeks.json")), Geek[].class));
  }

    public void addGeek(Geek geek) {
        for (int i = 0; i < geeks.size(); i++) {
            Geek g = geeks.get(i);
            if (Objects.equal(g.id(), geek.id())) {
                geeks.set(i, geek);
                return;
            }
        }

        geeks.add(geek);
    }

  @GET
  @Produces("application/json;charset=UTF-8")
  public List<Result> json(@QueryParam("q") String term) {
    List<Result> results = Lists.newArrayList();

    for (Geek geek : geeks) {
      if (geek.matches(term)) {
        results.add(geek.toResult());
      }
    }

    return results;
  }
}
