package com.nytimes.app.view.adapter;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nytimes.app.R;
import com.nytimes.app.databinding.ArticleListItemBinding;
import com.nytimes.app.model.Article;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sbingi on 3/31/2019.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>
                    implements Filterable {

    private ArticleItemClickListener listener;

    private List<Article> articles;
    private List<Article> filteredArticles;

    public ArticleAdapter(ArticleItemClickListener articleItemClickListener) {
        articles = filteredArticles = Collections.emptyList();
        listener = articleItemClickListener;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        this.filteredArticles = articles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int type) {
        ArticleListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.article_list_item, parent, false);
        binding.setAdapter(this);
        return new ArticleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder articleViewHolder, int position) {
        articleViewHolder.bindArticleView(filteredArticles.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredArticles.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchString = constraint.toString().toLowerCase();
                if (searchString.isEmpty()) {
                    filteredArticles = articles;
                } else {
                    List<Article> filteredArticlesNow = new ArrayList<>();
                    for (Article article : articles) {
                        if (article.title.toLowerCase().contains(searchString) ||
                                article.synopsis.toLowerCase().contains(searchString)) {
                            filteredArticlesNow.add(article);
                        }
                    }

                    filteredArticles = filteredArticlesNow;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredArticles;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredArticles = (ArrayList<Article>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        final ArticleListItemBinding binding;

        public ArticleViewHolder(@NonNull ArticleListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindArticleView(Article article) {
            binding.setArticle(article);
            binding.executePendingBindings();
        }
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, Article article) {
        Glide.with(imageView.getContext()).load(article.getSmallImageUrl()).into(imageView);
    }

    public void onViewHolderClick(View v, Article a) {
        listener.onArticlesItemClick(v, a);
    }

    public interface ArticleItemClickListener {
        void onArticlesItemClick(View view, Article article);
    }
}
