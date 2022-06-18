package com.example.newsapp;

import android.animation.ObjectAnimator;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.facebook.AccessToken;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import static com.example.newsapp.MainActivity.PREFERENCES_KEY;



public class NotificationsActivity extends AppCompatActivity {

    public static String CHANNEL_ID = "channel";
    ObjectAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);


        // bottom navigation
        BottomNavigationView nav = findViewById(R.id.bottom_navigation);

        nav.setSelectedItemId(R.id.notifications);

        nav.setOnNavigationItemSelectedListener(

                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.home:
                                Intent intent2 = new Intent(NotificationsActivity.this, MainActivity.class);
                                startActivity(intent2);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                break;

                            case R.id.account:
                                SharedPreferences preferences = getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
                                boolean IsLoggedIn = preferences.getBoolean("IsLoggedIn", false);
                                Intent intent1;

                                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

                                if(!IsLoggedIn && !isLoggedIn) {
                                    intent1 = new Intent(NotificationsActivity.this, LoginActivity.class);
                                }
                                else {
                                    intent1 = new Intent(NotificationsActivity.this, AccountActivity.class);
                                }

                                startActivity(intent1);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                break;

                            case R.id.notifications:
                                break;
                        }

                        return false;
                    }
                });


        Button btn = findViewById(R.id.notification_btn);

        animator = ObjectAnimator.ofArgb(this, "color", Color.BLUE, Color.RED);
        animator.setDuration(3000);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotif();
            }
        });

    }


    public void setColor(int color) {
        Button btn = findViewById(R.id.notification_btn);
        btn.setBackgroundColor(color);
    }


    private void createNotif() {

        Intent notificationIntent = new Intent(NotificationsActivity.this, MainActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(
                this,
                0,
                notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Notification")
                .setContentText("Description")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent)
                .setAutoCancel(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1, builder.build());

    }


    @Override
    public void onResume(){
        super.onResume();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}
