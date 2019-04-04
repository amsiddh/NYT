package com.nytimes.app.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.view.View;

import com.nytimes.app.NYTApplication;
import com.nytimes.app.R;
import com.nytimes.app.model.Article;
import com.nytimes.app.model.ArticleResponse;
import com.nytimes.app.network.ArticleService;
import com.nytimes.app.network.NYTApi;

import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sbingi on 3/29/2019.
 */
public class ArticleListViewModel extends ViewModel {

    @Inject
    public ArticleService articleService;

    @Inject
    NYTApplication application;

    public ObservableInt progressVisibility;
    public ObservableInt refreshButtonVisibility;
    public ObservableInt infoMessageVisibility;
    public ObservableInt layoutContentVisibility;
    public ObservableField<String> infoMessage;
    public ObservableField<String> toolbarSubtitle;

    private MutableLiveData<List<Article>> articleList;

    /**
     * It holds the current loaded article time period
     */
    private String currentArticlesTimePeriod;

    public ArticleListViewModel() {
        progressVisibility = new ObservableInt(View.INVISIBLE);
        layoutContentVisibility = new ObservableInt(View.INVISIBLE);
        infoMessageVisibility = new ObservableInt(View.INVISIBLE);
        refreshButtonVisibility = new ObservableInt(View.INVISIBLE);
        infoMessage = new ObservableField<>();
        toolbarSubtitle = new ObservableField<>();
    }

    /**
     * Get requested Articles from available {@link MutableLiveData}
     * otherwise from server
     * @return list of Articles in {@link MutableLiveData<List<Article>>}
     */
    public LiveData<List<Article>> getArticles() {
        if (articleList == null) {
            articleList = new MutableLiveData<>();
            loadArticles(application.getString(R.string.articles_in_one_day));
        }
        return articleList;
    }

    /**
     * Requested Articles from server only if currently loaded articles
     * are not same as requested.
     * @param timePeriod articles time period
     */
    public void loadArticles(final String timePeriod) {
        if (!isSameArticlesFetchRequest(timePeriod)) {
            setToolbarSubtitle(timePeriod);
            showLoadingView();
            articleService.popularArticles(timePeriod).enqueue(new Callback<ArticleResponse>() {
                @Override
                public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                    if (response.isSuccessful() && response.body() != null
                            && response.body().results != null) {
                        if (!response.body().results.isEmpty()) {
                            showResultView();
                            clearArticleList();
                            articleList.setValue(response.body().results);
                            currentArticlesTimePeriod = timePeriod;
                        } else {
                            showErrorView(R.string.no_articles);
                        }
                    } else if (response.code() == NYTApi.ErrorCode.UNAUTHORIZED) {
                        showErrorView(R.string.error_unauthorized);
                    } else if (response.code() == NYTApi.ErrorCode.LIMIT_REACHED) {
                        showErrorView(R.string.error_reached_limit);
                    }
                }

                @Override
                public void onFailure(Call<ArticleResponse> call, Throwable t) {
                    progressVisibility.set(View.INVISIBLE);
                    infoMessageVisibility.set(View.VISIBLE);
                    if (t instanceof UnknownHostException) {
                        showErrorView(R.string.no_network);
                    } else {
                        showErrorView(R.string.error_fetching_articles);
                    }
                }
            });
        }
    }

    private boolean isSameArticlesFetchRequest(String timePeriod) {
        return articleList != null &&
                articleList.getValue() != null &&
                !articleList.getValue().isEmpty() &&
                currentArticlesTimePeriod.equalsIgnoreCase(timePeriod);
    }

    private void clearArticleList() {
        if (articleList != null && articleList.getValue() != null) {
            articleList.getValue().clear();
        }
    }

    private void showLoadingView() {
        progressVisibility.set(View.VISIBLE);
        refreshButtonVisibility.set(View.INVISIBLE);
        layoutContentVisibility.set(View.INVISIBLE);
        infoMessageVisibility.set(View.INVISIBLE);
    }

    private void showResultView() {
        progressVisibility.set(View.INVISIBLE);
        refreshButtonVisibility.set(View.INVISIBLE);
        layoutContentVisibility.set(View.VISIBLE);
        infoMessageVisibility.set(View.INVISIBLE);
    }

    private void showErrorView(int resInt) {
        refreshButtonVisibility.set(View.VISIBLE);
        progressVisibility.set(View.INVISIBLE);
        layoutContentVisibility.set(View.INVISIBLE);
        infoMessageVisibility.set(View.VISIBLE);
        infoMessage.set(application.getString(resInt));
    }

    private void setToolbarSubtitle(String articleTimePeriod) {
        toolbarSubtitle.set(application.getString(R.string.toolbar_subtitle,
                articleTimePeriod, (Integer.valueOf(articleTimePeriod) == 1 ? "day" : "days")));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
