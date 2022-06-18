package com.example.newsapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    // https://newsapi.org/v2/everything?q=tesla&from=2021-04-07&sortBy=publishedAt&apiKey=b2da6398d1f0447bbf87e752ad1337b1
    // https://newsapi.org/v2/top-headlines?country=us&apiKey=b2da6398d1f0447bbf87e752ad1337b1

    @GET("top-headlines")
    Call<SearchArticleModel> getArticleList(
            @Query("country") String country,
            @Query("apikey") String apikey
    );

}
