package com.nutri.nutri;


import android.content.Intent;

import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class IntroActivity extends AppCompatActivity {

    private Handler mWaitHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                    startActivity(intent);
                    finish();
                }
                catch (Exception ignored)
                {
                    ignored.printStackTrace();
                }
            }
        }, 2000);  // Give a 2 seconds delay.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Remove all the callbacks otherwise navigation will execute even after activity is killed or closed.
        mWaitHandler.removeCallbacksAndMessages(null);
    }
}

