package com.nytimes.app.view;

import android.app.SearchManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.nytimes.app.NYTApplication;
import com.nytimes.app.R;
import com.nytimes.app.databinding.ActivityArticleListBinding;
import com.nytimes.app.model.Article;
import com.nytimes.app.view.adapter.ArticleAdapter;
import com.nytimes.app.viewmodel.ArticleListViewModel;

import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ArticleDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ArticleListActivity extends AppCompatActivity implements
        ArticleAdapter.ArticleItemClickListener, SearchView.OnQueryTextListener {

    private ArticleListViewModel articleListViewModel;

    private ActivityArticleListBinding binding;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_article_list);
        articleListViewModel = ViewModelProviders.of(this).get(ArticleListViewModel.class);
        binding.setViewModel(articleListViewModel);

        ((NYTApplication)getApplication()).getAppComponent().inject(articleListViewModel);

        setupToolbar(binding.toolbar);

        setupRecyclerView(binding.layoutContent.articleList);


        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        articleListViewModel.getArticles().observe(this, new Observer<List<Article>>() {
            @Override
            public void onChanged(@Nullable List<Article> articles) {
                setArticlesData(articles);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) menu.findItem(R.id.item_search).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setMaxWidth(Integer.MAX_VALUE);
            searchView.setOnQueryTextListener(this);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_in_one_day:
                articleListViewModel.loadArticles(getString(R.string.articles_in_one_day));
                return true;
            case R.id.item_in_seven_days:
                articleListViewModel.loadArticles(getString(R.string.articles_in_seven_day));
                return true;
            case R.id.item_in_thirty_days:
                articleListViewModel.loadArticles(getString(R.string.articles_in_thirty_day));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        ArticleAdapter articleAdapter = new ArticleAdapter(this);
        recyclerView.setAdapter(articleAdapter);
    }

    private void setArticlesData(List<Article> articles) {
        ArticleAdapter articleAdapter =
                ((ArticleAdapter)binding.layoutContent.articleList.getAdapter());
        if (articleAdapter != null && articles != null) {
            articleAdapter.setArticles(articles);
        }
    }

    @Override
    public void onArticlesItemClick(View view, Article article) {
        if (mTwoPane) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.item_detail_container, ArticleDetailFragment.newInstance(article))
                    .commit();
        } else {
            Intent intent = new Intent(this, ArticleDetailActivity.class);
            intent.putExtra(ArticleDetailFragment.ARG_ARTICLE_ITEM, article);
            startActivity(intent);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        setSearchFilter(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        setSearchFilter(s);
        return false;
    }

    private void setSearchFilter(String query) {
        ArticleAdapter articleAdapter =
                ((ArticleAdapter)binding.layoutContent.articleList.getAdapter());
        if (articleAdapter != null) {
            articleAdapter.getFilter().filter(query);
        }
    }

    public void onReloadClick(View view) {
        articleListViewModel.loadArticles(getString(R.string.articles_in_one_day));
    }
}
