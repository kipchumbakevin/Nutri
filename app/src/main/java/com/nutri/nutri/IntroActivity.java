package com.nutri.nutri;


import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.VideoView;

public class IntroActivity extends AppCompatActivity {

    private Handler mWaitHandler = new Handler();
    VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        videoView = findViewById(R.id.videoSplash);
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
        }, 5000);  // Give a 5 seconds delay.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Remove all the callbacks otherwise navigation will execute even after activity is killed or closed.
        mWaitHandler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String videoPath = "android.resource://com.nutri.nutri/"+ R.raw.nutri;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        videoView.start();
    }
}

