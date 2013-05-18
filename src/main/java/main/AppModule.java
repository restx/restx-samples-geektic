package main;

import com.google.common.base.Charsets;
import restx.SignatureKey;
import restx.factory.Module;
import restx.factory.Provides;
import restx.jongo.JongoFactory;

import javax.inject.Named;

@Module
public class AppModule {
    @Provides @Named(JongoFactory.JONGO_DB_NAME)
    public String jongoDbName() {
        return System.getProperty("mongo.db", "geektic");
    }

    @Provides
    public SignatureKey signatureKey() {
       return new SignatureKey("this is the key for geektic 1234".getBytes(Charsets.UTF_8));
    }



}
