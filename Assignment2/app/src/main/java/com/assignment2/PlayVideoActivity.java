package com.assignment2;

//Renrui Liu 216166456, SIT207 assignment2.

/*
This activity has a VideoView which plays the guide video.
 *  */

/*
References:

How to Play Video in Android Studio 1.4
Culture - Audio Books - LivresAudio
https://www.youtube.com/watch?v=voYDlnfcchs

* */

import android.graphics.PixelFormat;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class PlayVideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        getWindow().setFormat(PixelFormat.UNKNOWN);
        VideoView mVideoView=(VideoView)findViewById(R.id.videoView);
        String uriPath = "android.resource://com.assignment2/"+R.raw.appdescription;
        Uri uri=Uri.parse(uriPath);
        mVideoView.setVideoURI(uri);
        mVideoView.requestFocus();
        mVideoView.start();
    }
}
