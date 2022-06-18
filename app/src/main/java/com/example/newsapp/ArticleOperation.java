package com.example.newsapp;

public interface ArticleOperation {

    void insertArticles(String result);

    boolean findArticle(ArticleDBModel articleDBModel);
}
