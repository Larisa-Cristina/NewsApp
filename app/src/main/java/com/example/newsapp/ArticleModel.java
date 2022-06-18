package com.example.newsapp;

import android.os.Parcel;
import android.os.Parcelable;

public class ArticleModel implements Parcelable {

    private Source source;
    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;
    private String content;


    public ArticleModel(Source source, String author, String title, String description, String url, String urlToImage, String publishedAt, String content) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }


    public ArticleModel(Parcel in) {
        String[] data = new String[9];

        in.readStringArray(data);

        this.source = new Source(data[0], data[1]);
        this.author = data[2];
        this.title = data[3];
        this.description = data[4];
        this.url = data[5];
        this.urlToImage = data[6];
        this.publishedAt = data[7];
        this.content = data[8];
    }


    public Source getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getContent() {
        return content;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.source.getId(), this.source.getName(), this.author, this.title, this.description, this.url, this.urlToImage, this.publishedAt, this.content });
    }

    public static final Creator CREATOR = new Creator() {
        public ArticleModel createFromParcel(Parcel in) { return new ArticleModel(in); }
        public ArticleModel[] newArray(int size) {return new ArticleModel[size]; }
    };

}
