package com.nytimes.app;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sbingi on 3/31/2019.
 */
@Module
public class AppModule {

    private final NYTApplication nytApplication;

    public AppModule(NYTApplication nytApplication) {
        this.nytApplication = nytApplication;
    }

    @Provides
    @Singleton
    NYTApplication provideNYTApplication() {
        return nytApplication;
    }
}
