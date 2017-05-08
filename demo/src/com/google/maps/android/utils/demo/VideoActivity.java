package com.google.maps.android.utils.demo;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;


public class VideoActivity extends Activity {

    private VideoView myVideoView;
    private int position = 0;
    private ProgressDialog progressDialog;
    private MediaController mediaControls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        if (mediaControls == null) {
            mediaControls = new MediaController(VideoActivity.this);
        }

        myVideoView = (VideoView) findViewById(R.id.video_view);
        progressDialog = new ProgressDialog(VideoActivity.this);
        progressDialog.setTitle("Demo Video");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        try {
            myVideoView.setMediaController(mediaControls);
            myVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.polecleansingvideo));

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        myVideoView.requestFocus();
        myVideoView.setOnPreparedListener(new OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                progressDialog.dismiss();
                myVideoView.seekTo(position);
                if (position == 0) {
                    myVideoView.start();
                } else {
                    myVideoView.pause();
                }

            }

        });

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());
        myVideoView.pause();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("Position");
        myVideoView.seekTo(position);
    }




}
