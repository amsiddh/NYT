package com.nytimes.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sbingi on 3/31/2019.
 */
public class ArticleResponse {

    private String status;
    private String copyright;
    @SerializedName("num_results")
    private Integer numResults;
    private List<Article> results;

    public String getStatus() {
        return status;
    }

    public String getCopyright() {
        return copyright;
    }

    public Integer getNumResults() {
        return numResults;
    }

    public List<Article> getResults() {
        return results;
    }
}
