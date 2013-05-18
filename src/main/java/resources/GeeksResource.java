package resources;

import com.google.common.collect.Lists;
import geeks.Geek;
import geeks.Result;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.jongo.JongoCollection;

import javax.inject.Named;
import java.util.List;

@Component @RestxResource
public class GeeksResource {
    private final JongoCollection geeks;

    public GeeksResource(@Named("geeks") JongoCollection geeks) {
        this.geeks = geeks;
    }

    @POST("/geeks")
    public Result addGeek(Geek geek) {
        geeks.get().save(geek);
        return geek.toResult();
    }

  @GET("/geeks")
  public List<Result> search(String q) {
    List<Result> results = Lists.newArrayList();

    for (Geek geek : geeks.get().find().as(Geek.class)) {
      if (geek.matches(q)) {
        results.add(geek.toResult());
      }
    }

    return results;
  }
}
