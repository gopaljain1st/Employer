package com.example.employeetesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import pl.droidsonroids.gif.GifImageView;

public class SplashScreen extends AppCompatActivity {

    GifImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        image=findViewById(R.id.splash_screen);
        new MyThread().start();


        new MyThread().start();
    }

    class MyThread extends Thread
    {
        @Override
        public void run()
        {
            super.run();
            try {
                Thread.sleep(6000);
                Intent in=new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(in);
                finish();
            }
            catch (Exception e)
            {

            }
        }
    }
}
