package com.nytimes.app.network;

import com.nytimes.app.model.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sbingi on 3/31/2019.
 */
public interface ArticleService {

    @GET(NYTApi.MOST_POPULAR_ARTICLES)
    Call<ArticleResponse> popularArticles(@Path(NYTApi.Params.TIME_PERIOD) String timePeriod);
}
