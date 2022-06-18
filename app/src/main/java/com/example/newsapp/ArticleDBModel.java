package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "article")
public class ArticleDBModel {

    public String source;

    public String author;

    @PrimaryKey
    @NonNull
    public String title;

    public String description;

    public String url;

    public String urlToImage;

    public String publishedAt;

    public String content;


    public ArticleDBModel(String source, String author, String title, String description, String url, String urlToImage, String publishedAt, String content) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }
}
