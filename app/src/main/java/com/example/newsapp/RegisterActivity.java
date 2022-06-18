package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.newsapp.MainActivity.PREFERENCES_KEY;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText email = findViewById(R.id.register_email);
        final EditText password = findViewById(R.id.register_password);
        Button btn = findViewById(R.id.register_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);

                String email1 = email.getText().toString();
                String password1 = password.getText().toString();

                SharedPreferences.Editor editor = preferences.edit();

                editor.putString("email", email1);
                editor.putString("password", password1);
                editor.apply();

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });


        TextView register = findViewById(R.id.goto_login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });


        // bottom navigation
        BottomNavigationView nav = findViewById(R.id.bottom_navigation);

        nav.setSelectedItemId(R.id.account);

        nav.setOnNavigationItemSelectedListener(

                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.account:
                                break;
                            case R.id.home:
                                Intent intent1 = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent1);
                                break;
                            case R.id.notifications:
                                Intent intent2 = new Intent(RegisterActivity.this, NotificationsActivity.class);
                                startActivity(intent2);
                                break;
                        }

                        return false;
                    }
                });
    }
}
