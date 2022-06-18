package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnItemClickListener, ArticleOperation {

    public final static String PREFERENCES_KEY = "preferences key";
    public static String ARTICLE = "article";

    private ArticleAdapter adapter;
    private List<ArticleModel> articleModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Call<SearchArticleModel> call = ApiBuilder.getInstance().getArticleList(
                "us",
                ApiBuilder.API_KEY
        );

        call.enqueue(new Callback<SearchArticleModel>() {
            @Override
            public void onResponse(Call<SearchArticleModel> call, Response<SearchArticleModel> response) {

                List<ArticleModel> list = response.body().getArticles();
                articleModels = list;

                adapter = new ArticleAdapter(list, (OnItemClickListener) MainActivity.this::onItemClick);

                RecyclerView rv = findViewById(R.id.recycler_view);
                rv.setAdapter(adapter);

                insertArticles(list);
            }

            @Override
            public void onFailure(Call<SearchArticleModel> call, Throwable t) {

            }
        });


        // Search
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);


        // bottom navigation
        BottomNavigationView nav = findViewById(R.id.bottom_navigation);

        nav.setSelectedItemId(R.id.home);

        nav.setOnNavigationItemSelectedListener(

                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.home:
                                break;

                            case R.id.account:
                                SharedPreferences preferences = getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
                                boolean IsLoggedIn = preferences.getBoolean("IsLoggedIn", false);
                                Intent intent1;

                                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

                                if(!IsLoggedIn && !isLoggedIn) {
                                    intent1 = new Intent(MainActivity.this, LoginActivity.class);
                                }
                                else {
                                    intent1 = new Intent(MainActivity.this, AccountActivity.class);
                                }

                                startActivity(intent1);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                break;

                            case R.id.notifications:
                                Intent intent2 = new Intent(MainActivity.this, NotificationsActivity.class);
                                startActivity(intent2);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                break;
                        }

                        return false;
                    }
                });
    }


    @Override
    public void onItemClick(ArticleModel item) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARTICLE, item);

        Intent intent = new Intent(MainActivity.this, ArticleActivity.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }


    @Override
    public void onResume(){
        super.onResume();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }


    public void insertArticles(List<ArticleModel> list) {

        for (ArticleModel a : list) {
            String t = a.getTitle();

            new FindArticleOperation(this).execute(t);

            ArticleDBModel articleDBModel = new ArticleDBModel(
                    a.getSource().getName(),
                    a.getAuthor(),
                    a.getTitle(),
                    a.getDescription(),
                    a.getUrl(),
                    a.getUrlToImage(),
                    a.getPublishedAt(),
                    a.getContent()
            );
            new InsertArticleOperation(this).execute(articleDBModel);

        }
    }

    @Override
    public void insertArticles(String result) {
    }

    @Override
    public boolean findArticle(ArticleDBModel articleDBModel) {
        if (articleDBModel != null)
            return true;
        else
            return false;
    }



    // Search

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
        return true;
    }


    private void filter(String text) {
        ArrayList<ArticleModel> filteredlist = new ArrayList<>();

        for (ArticleModel item : articleModels) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filteredlist);
        }
    }


}