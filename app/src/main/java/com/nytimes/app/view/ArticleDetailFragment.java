package com.nytimes.app.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nytimes.app.R;
import com.nytimes.app.databinding.FragmentArticleDetailBinding;
import com.nytimes.app.model.Article;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ArticleListActivity}
 * in two-pane mode (on tablets) or a {@link ArticleDetailActivity}
 * on handsets.
 */
public class ArticleDetailFragment extends Fragment {

    public static final String ARG_ARTICLE_ITEM = "article_item";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticleDetailFragment() {
        // unused
    }

    public static ArticleDetailFragment newInstance(Article article) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_ARTICLE_ITEM, article);
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentArticleDetailBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_article_detail, container, false);
        View view = binding.getRoot();
        if (getArguments() != null && getArguments().containsKey(ARG_ARTICLE_ITEM)) {
            Article article = getArguments().getParcelable(ARG_ARTICLE_ITEM);
            binding.setArticle(article);
        }
        return view;
    }
}
