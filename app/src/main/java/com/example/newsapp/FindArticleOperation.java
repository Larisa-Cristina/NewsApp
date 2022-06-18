package com.example.newsapp;

import android.os.AsyncTask;

public class FindArticleOperation extends AsyncTask<String, Void, ArticleDBModel> {

    ArticleOperation listener;

    FindArticleOperation(ArticleOperation listener) {
        this.listener = listener;
    }

    @Override
    protected ArticleDBModel doInBackground(String... strings) {
        String title = strings[0];
        return MyApplication.getAppDatabase().articleDao().findArticleWithTitle(title);
    }

    @Override
    protected void onPostExecute(ArticleDBModel articleDBModel) {
        listener.findArticle(articleDBModel);
    }


}
