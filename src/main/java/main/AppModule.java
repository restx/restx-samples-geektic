package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.io.Resources;
import geeks.Geek;
import resources.GeeksResource;
import restx.factory.Module;
import restx.factory.Provides;

import java.io.IOException;
import java.util.List;

@Module
public class AppModule {
    @Provides
    public GeeksResource searchResource(ObjectMapper mapper) {
        try {
            List<Geek> geeks = Lists.newArrayList(mapper.reader(Geek.class).<Geek>readValues(Resources.getResource("geeks.json")));
            return new GeeksResource(geeks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
