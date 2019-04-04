package com.nytimes.app;

import android.app.Application;

import com.nytimes.app.network.NetworkModule;

/**
 * Created by sbingi on 3/31/2019.
 */
public class NYTApplication extends Application {

    protected AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Dagger
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .networkModule(new NetworkModule())
                .build();

        appComponent.inject(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
