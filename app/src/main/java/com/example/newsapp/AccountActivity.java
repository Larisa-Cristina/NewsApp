package com.example.newsapp;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.example.newsapp.MainActivity.PREFERENCES_KEY;

import com.facebook.login.LoginManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.ByteArrayOutputStream;

public class AccountActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        TextView email = findViewById(R.id.email_view);
        ImageView pfp = findViewById(R.id.pfp);
        TextView editpfp = findViewById(R.id.edit_pfp);

        SharedPreferences preferences = getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
        String email1 = preferences.getString("email", null);
        String pfp1 = preferences.getString("pfp", null);

        email.setText(email1);

        if (pfp1 != null) {
            // decode image
            byte[] b = Base64.decode(pfp1, Base64.DEFAULT);
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            // display image
            pfp.setImageBitmap(imageBitmap);
        }


        // take picture
        editpfp.setOnClickListener(view -> {

            // request permission
            if (ContextCompat.checkSelfPermission(AccountActivity.this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AccountActivity.this, new String[] {
                        Manifest.permission.CAMERA
                }, REQUEST_IMAGE_CAPTURE);
            }
            else {
                // take picture
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } catch (ActivityNotFoundException e) {
                    // display error state to the user
                }
            }

        });


        Button btn = findViewById(R.id.logout_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences preferences = getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = preferences.edit();

                editor.clear();
                editor.apply();


                LoginManager.getInstance().logOut();

                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
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

                            case R.id.home:
                                Intent intent1 = new Intent(AccountActivity.this, MainActivity.class);
                                startActivity(intent1);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                break;

                            case R.id.account:
                                break;

                            case R.id.notifications:
                                Intent intent2 = new Intent(AccountActivity.this, NotificationsActivity.class);
                                startActivity(intent2);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                break;
                        }

                        return false;
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            // display image
            ImageView pfp = findViewById(R.id.pfp);
            pfp.setImageBitmap(imageBitmap);

            // save image in shared preferences
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] b = byteArrayOutputStream.toByteArray();

            String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

            SharedPreferences preferences = getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("pfp", encodedImage);
            editor.apply();
        }
    }

}
