package com.nutri.nutri;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

public class RecipesActivity extends AppCompatActivity {
VideoView videoView;
MediaController mediaController;
ImageView play;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
//        String videoPath = "android.resource://com.nutri.nutri/"+ R.raw.young;
//        videoView = findViewById(R.id.videoView);
//        play = findViewById(R.id.videoPlay);
//        mediaController = new MediaController(this);
//        Uri uri = Uri.parse(videoPath);
//        videoView.setVideoURI(uri);
//        videoView.setMediaController(mediaController);
//        mediaController.setAnchorView(videoView);
//        play.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                videoView.start();
//            }
//        });

        setTitle("Recipes");
    }
}
