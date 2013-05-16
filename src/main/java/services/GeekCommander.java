package services;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import geeks.Geek;
import resources.GeeksResource;

import javax.inject.Inject;
import java.util.List;

/**
 * User: xavierhanin
 * Date: 3/23/13
 * Time: 1:42 PM
 */
public class GeekCommander {
    private GeeksResource geeksResource;

    @Inject
    public GeekCommander(GeeksResource geeksResource) {
        this.geeksResource = geeksResource;
    }

    public GeekCommand parse(String twitterAccount, String name, String pictureUrl,  String status) {
        Geek geek = new Geek();
        geek.twitterAccount = twitterAccount;
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

        return new GeekCommand(geeksResource, geek);
    }

    public static class GeekCommand implements Runnable {
        private final GeeksResource geeksResource;
        private final Geek geek;

        public GeekCommand(GeeksResource geeksResource, Geek geek) {
            this.geeksResource = geeksResource;
            this.geek = geek;
        }

        public Geek getGeek() {
            return geek;
        }

        @Override
        public void run() {
            geeksResource.addGeek(geek);
        }
    }


}
