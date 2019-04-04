package com.nytimes.app.network;

import com.nytimes.app.BuildConfig;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sbingi on 3/31/2019.
 */
@Module
public class NetworkModule {

    @Provides
    @Singleton
    OkHttpClient provideHttpClient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);

        return httpClient.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(NYTApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        return builder.client(client).build();
    }

    @Provides
    @Singleton
    ArticleService provideArticleService(Retrofit retrofit){
        return retrofit.create(ArticleService.class);
    }
}
