package com.nytimes.app;

import android.app.Application;

import com.nytimes.app.network.NetworkModule;
import com.nytimes.app.viewmodel.ArticleListViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by sbingi on 3/31/2019.
 */
@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(Application app);
    void inject(ArticleListViewModel articleListViewModel);
}
