package team10.hkr.challengeapp.Controllers;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.VideoView;

import team10.hkr.challengeapp.R;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog mDialog;
    VideoView videoView;
    ImageButton playPauseButton;
    String intentData;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = (VideoView) findViewById(R.id.video_view);
        playPauseButton = (ImageButton) findViewById(R.id.btn_play_pause);
        playPauseButton.setOnClickListener(this);

        mDialog = new ProgressDialog(VideoActivity.this);
        mDialog.setMessage("Please wait...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();

        intentData = getIntent().getStringExtra("VideoURL");
        uri = Uri.parse(intentData);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mDialog.dismiss();
                mp.setLooping(true);
                videoView.start();
                playPauseButton.setImageResource(R.drawable.ic_pause_button);
            }
        });
        if (videoView.isPlaying()) {
            playPauseButton.setImageResource(R.drawable.ic_pause_button);
        }
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playPauseButton.setImageResource(R.drawable.ic_play_arrow);
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (videoView.isPlaying()) {
            videoView.pause();
            playPauseButton.setImageResource(R.drawable.ic_play_arrow);
        }
        if (!videoView.isPlaying()) {
            videoView.resume();
            playPauseButton.setImageResource(R.drawable.ic_pause_button);
        }
    }

    @Override
    public void onBackPressed() {
        videoView.setVideoURI(null);
        videoView.setMediaController(null);
        uri = null;
        intentData = null;
        super.onBackPressed();
    }
}