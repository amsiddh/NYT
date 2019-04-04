package com.nytimes.app.view;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nytimes.app.R;
import com.nytimes.app.databinding.ActivityArticleDetailBinding;
import com.nytimes.app.model.Article;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ArticleListActivity}.
 */
public class ArticleDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ActivityArticleDetailBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_article_detail);
        Article article = getIntent().getParcelableExtra(ArticleDetailFragment.ARG_ARTICLE_ITEM);
        if (article != null) {
            binding.setArticle(article);
        }

        setupToolbar(binding.detailToolbar);

        addDetailFragment(savedInstanceState, article);
    }

    private void setupToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void addDetailFragment(Bundle savedInstanceState, Article article) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, ArticleDetailFragment.newInstance(article))
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @BindingAdapter({"headerImageUrl"})
    public static void loadHeaderImage(ImageView imageView, Article article) {
        Glide.with(imageView.getContext()).load(article.getSmallImageUrl()).into(imageView);
    }
}
