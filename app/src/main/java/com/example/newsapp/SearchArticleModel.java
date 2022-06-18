package com.example.newsapp;

import java.util.List;

public class SearchArticleModel {

    private List<ArticleModel> articles;
    private Long totalResults;

    public List<ArticleModel> getArticles() {
        return articles;
    }

    public Long getTotalResults() {
        return totalResults;
    }
}
