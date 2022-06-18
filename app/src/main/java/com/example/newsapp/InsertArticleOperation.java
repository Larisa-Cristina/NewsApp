package com.example.newsapp;

import android.os.AsyncTask;

public class InsertArticleOperation extends AsyncTask<ArticleDBModel, Void, String> {

    ArticleOperation listener;

    public InsertArticleOperation(ArticleOperation listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(ArticleDBModel... articleDBModels) {
        try {
            for (ArticleDBModel a : articleDBModels) {
                ArticleDBModel a1 = MyApplication.getAppDatabase().articleDao().findArticleWithTitle(a.title);
                if (a1 == null) {
                    MyApplication.getAppDatabase().articleDao().insertAll(articleDBModels);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            return "error";
        }
        return "success";
    }


    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        listener.insertArticles(result);
    }
}
