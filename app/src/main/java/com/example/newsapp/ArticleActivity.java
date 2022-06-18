package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.example.newsapp.MainActivity.PREFERENCES_KEY;
import static com.example.newsapp.MainActivity.ARTICLE;


public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        Intent intent = getIntent();
        ArticleModel article = intent.getParcelableExtra(ARTICLE);

        TextView article_title = findViewById(R.id.article_title);
        article_title.setText(article.getTitle());

        TextView article_content = findViewById(R.id.article_content);
        article_content.setText(article.getContent());


        // Share

        Button shareBtn = findViewById(R.id.shareBtn);

        shareBtn.setOnClickListener(view -> {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, article.getTitle());
            shareIntent.putExtra(Intent.EXTRA_TEXT, article.getContent());
            shareIntent.setType("message/rfc822");

            startActivity(Intent.createChooser(shareIntent, null));
        });


        // bottom navigation
        BottomNavigationView nav = findViewById(R.id.bottom_navigation);

        nav.setSelectedItemId(R.id.home);

        nav.setOnNavigationItemSelectedListener(

                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.account:
                                SharedPreferences preferences = getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
                                boolean IsLoggedIn = preferences.getBoolean("IsLoggedIn", false);
                                Intent intent1;

                                if(!IsLoggedIn) {
                                    intent1 = new Intent(ArticleActivity.this, LoginActivity.class);
                                }
                                else {
                                    intent1 = new Intent(ArticleActivity.this, AccountActivity.class);
                                }

                                startActivity(intent1);
                                break;

                            case R.id.home:
                                Intent intent3 = new Intent(ArticleActivity.this, MainActivity.class);
                                startActivity(intent3);
                                break;

                            case R.id.notifications:
                                Intent intent2 = new Intent(ArticleActivity.this, NotificationsActivity.class);
                                startActivity(intent2);
                                break;
                        }

                        return false;
                    }
                });

    }
}
