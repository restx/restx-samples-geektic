package resources;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import geeks.Geek;
import geeks.Result;
import restx.annotations.GET;
import restx.annotations.RestxResource;

import java.util.List;

@RestxResource
public class GeeksResource {
    private List<Geek> geeks;

    public GeeksResource() {
        this(Lists.<Geek>newArrayList());
    }

    public GeeksResource(List<Geek> geeks) {
        this.geeks = geeks;
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

  @GET("/search")
  public List<Result> json(String q) {
    List<Result> results = Lists.newArrayList();

    for (Geek geek : geeks) {
      if (geek.matches(q)) {
        results.add(geek.toResult());
      }
    }

    return results;
  }
}
