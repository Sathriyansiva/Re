package com.ltvscatalogue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import Shared.Config;

public class Splash extends AppCompatActivity {
    private static final long SPLASH_DISPLAY_LENGTH = 1500;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean isLoggedin=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setTheme(R.style.splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedPreferences = Splash.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                isLoggedin = sharedPreferences.getBoolean("KEY_isLoggedin", false);
                if (isLoggedin) {
                    Intent intent = new Intent(Splash.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent mainIntent = new Intent(Splash.this, Login.class);
                    startActivity(mainIntent);
                    finish();
                }

            }

        }, SPLASH_DISPLAY_LENGTH);



    }

}

