package com.example.newsapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ArticleDao {

    @Insert
    void insertAll(ArticleDBModel... articleDBModel);

    @Update
    void updateArticle(ArticleDBModel... articleDBModel);

    @Delete
    void delete(ArticleDBModel articleDBModel);


    @Query("SELECT * FROM article")
    public List<ArticleDBModel> getAll();

    @Query("SELECT * FROM article WHERE title LIKE :search LIMIT 1")
    ArticleDBModel findArticleWithTitle(String search);

}
