package com.nytimes.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sbingi on 3/31/2019.
 */
public class ArticleResponse {

    public String status;
    public String copyright;
    @SerializedName("num_results")
    public Integer numResults;
    public List<Article> results;

}
