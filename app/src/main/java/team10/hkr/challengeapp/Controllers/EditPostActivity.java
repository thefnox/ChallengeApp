package team10.hkr.challengeapp.Controllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import team10.hkr.challengeapp.AppSingleton;
import team10.hkr.challengeapp.Controllers.SettingsActivity.CloseAccountActivity;
import team10.hkr.challengeapp.Models.User;
import team10.hkr.challengeapp.R;
import team10.hkr.challengeapp.RequestQueueSingleton;
import team10.hkr.challengeapp.SharedPref;

public class EditPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        populateContentView();
        populateDescription();

        Button editPostBtn = (Button) findViewById(R.id.editPostButton);

        editPostBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(EditPostActivity.this)

                        .setTitle("Edit Post")
                        .setMessage("Are you sure you want to edit this post?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                editPost();

                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    private void editPost() {

        final EditText description = (EditText) findViewById(R.id.editDescriptionText);

        final String DESCRIPTION = "description";
        String URL = "http://95.85.16.177:3000/api/post/" + getIntent().getStringExtra("postUUID");

        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(EditPostActivity.this,"Post Edited", Toast.LENGTH_LONG).show();
                Intent mIntent = new Intent(EditPostActivity.this, ProfileActivity.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(mIntent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditPostActivity.this, "Something went wrong. Post not edited", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put(DESCRIPTION, description.getText().toString());
                return params;
            }
        };

        RequestQueueSingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void populateDescription() {

        EditText description = (EditText) findViewById(R.id.editDescriptionText);
        description.setText(getIntent().getStringExtra("postDescription"));

    }

    public void populateContentView(){

        ImageView contentIfPhotoView = (ImageView) findViewById(R.id.editPostImage);
        VideoView contentIfVideoView = (VideoView) findViewById(R.id.editPostVideo);

        String CONTENT_URL = getIntent().getStringExtra("contentURL");
        String staticURL = getIntent().getStringExtra("staticURL");

        try {
            if (isJpg(CONTENT_URL)) {
                Log.d("halala", staticURL);
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(CONTENT_URL).getContent());
                contentIfPhotoView.setImageBitmap(bitmap);
                contentIfPhotoView.setVisibility(View.VISIBLE);

            } else if (CONTENT_URL.endsWith(".mp4")) {
                //Show video here
                contentIfVideoView.setVisibility(View.VISIBLE);
                contentIfVideoView.seekTo(3000);
                final MediaController mediaController = new MediaController(this);
                mediaController.setAnchorView(contentIfVideoView);
                Uri video_uri = Uri.parse(CONTENT_URL);
                contentIfVideoView.setVideoURI(video_uri);
                contentIfVideoView.setMediaController(mediaController);
                Log.d("CheckURLifValid", CONTENT_URL);

            } else {
                contentIfPhotoView.setImageResource(R.drawable.invalid_content);
                contentIfPhotoView.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean isJpg(String url) {
        if (url.length() > 4) {
            String tester = url.split("")[url.length()-2] + url.split("")[url.length()-1] + url.split("")[url.length()];
            Log.d("JPGTESTERURL",url);
            Log.d("JPGTESTER", tester);
            if(tester.equals("jpg")) {
                return true;
            }
        }
        return false;
    }
}
