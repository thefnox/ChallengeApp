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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

//                    Uri video_uri = Uri.parse(CONTENT_URL);
//                    holder.contentIfVideoView.setVideoURI(video_uri);
//                    holder.contentIfVideoView.setMediaController(mediaController);
//                    Log.d("CheckURLifValid", CONTENT_URL);


        videoView = (VideoView) findViewById(R.id.video_view);
        playPauseButton = (ImageButton) findViewById(R.id.btn_play_pause);
        playPauseButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mDialog = new ProgressDialog(VideoActivity.this);
        mDialog.setMessage("Please wait...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        try {
            if(!videoView.isPlaying()) {
                Log.d("VideoURL:", getIntent().getStringExtra("VideoURL"));
                Uri uri = Uri.parse(getIntent().getStringExtra("VideoURL"));
                videoView.setVideoURI(uri);
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        playPauseButton.setImageResource(R.drawable.ic_play_arrow);
                    }
                });
            } else {
                videoView.pause();
                playPauseButton.setImageResource(R.drawable.ic_play_arrow);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
    }
}
