package services;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import geeks.Geek;
import resources.SearchResource;

import javax.inject.Inject;
import java.util.List;

/**
 * User: xavierhanin
 * Date: 3/23/13
 * Time: 1:42 PM
 */
public class GeekCommander {
    private SearchResource searchResource;

    @Inject
    public GeekCommander(SearchResource searchResource) {
        this.searchResource = searchResource;
    }

    public GeekCommand parse(String name, String pictureUrl,  String status) {
        Geek geek = new Geek();
        geek.prenom = name;
        geek.nom = "";

        int i = name.indexOf(" ");
        if (i != -1) {
            geek.prenom = name.substring(0, i).trim();
            geek.nom = name.substring(i + 1).trim();
        }

        List<String> args = Lists.newArrayList(Splitter.on(" ").split(status));

        int likes = args.indexOf("#likes");
        if (likes != -1) {
            geek.likes = Lists.newArrayList(args.subList(likes + 1, args.size()));
        }

        geek.pictureUrl = pictureUrl;

        return new GeekCommand(searchResource, geek);
    }

    public static class GeekCommand implements Runnable {
        private final SearchResource searchResource;
        private final Geek geek;

        public GeekCommand(SearchResource searchResource, Geek geek) {
            this.searchResource = searchResource;
            this.geek = geek;
        }

        public Geek getGeek() {
            return geek;
        }

        @Override
        public void run() {
            searchResource.addGeek(geek);
        }
    }


}
