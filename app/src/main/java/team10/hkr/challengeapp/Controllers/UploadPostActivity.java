package team10.hkr.challengeapp.Controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import team10.hkr.challengeapp.Controllers.SettingsActivity.CloseAccountActivity;
import team10.hkr.challengeapp.MultipartRequestLibraryCode.AppHelper;
import team10.hkr.challengeapp.MultipartRequestLibraryCode.VolleyMultipartRequest;
import team10.hkr.challengeapp.R;
import team10.hkr.challengeapp.RequestQueueSingleton;
import team10.hkr.challengeapp.SharedPref;

public class UploadPostActivity extends Activity {

    ImageView photo;
    private static final Pattern HASHTAG_MATCH =
            Pattern.compile("(?:^|\\s|[\\p{Punct}&&[^/]])(#[\\p{L}0-9-_]+)");

    static final int CAM_REQUEST = 1;
    static final int VIDEO_REQUEST = 2;
    private ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_post);

        SharedPref.init(this);
        final Button takePhotoBtn = (Button) findViewById(R.id.takePhotoButton);
        Button takeVideoBtn = (Button) findViewById(R.id.recordVideoButton);

        Button uploadPostBtn = (Button) findViewById(R.id.uploadButton);

        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoButton();
            }
        });

        takeVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordVideoButton();
            }
        });

        uploadPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(UploadPostActivity.this)

                        .setTitle("Upload Post")
                        .setMessage("Are you sure you want to upload this post?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                if (SharedPref.read("fileName", "").endsWith(".jpg")){
                                    try {
                                        uploadPhotoPost();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                else if (SharedPref.read("fileName", "").endsWith(".mp4")){

                                    try {
                                        uploadVideoPost();
                                    } catch (JSONException e){
                                        e.printStackTrace();
                                    }
                                }
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

    private void recordVideoButton() {

        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        String fileName = getVideoFileName();

        SharedPref.write("videoFileName", fileName);
        SharedPref.write("fileName", fileName );

        File takenVideo = new File(externalStoragePublicDirectory, fileName);
        Uri videoURI = Uri.fromFile(takenVideo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI);

        startActivityForResult(intent, VIDEO_REQUEST);

    }

    public void takePhotoButton(){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String fileName = getPhotoFileName();

        SharedPref.write("photoFileName", fileName);
        SharedPref.write("fileName", fileName);
        File takenPhoto = new File(externalStoragePublicDirectory, fileName);
        Uri imageURI = Uri.fromFile(takenPhoto);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);

        startActivityForResult(intent, CAM_REQUEST);
    }

    private String getPhotoFileName() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmSS");
        String timestamp = sdf.format(new Date());
        return "ChallengeAppPhoto" + timestamp + ".jpg";
    }

    private String getVideoFileName() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmSS");
        String timestamp = sdf.format(new Date());
        return "ChallengeAppVideo" + timestamp + ".mp4";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView showPhoto = (ImageView) findViewById(R.id.takenPhotoImageView);

        if (requestCode == CAM_REQUEST) {

            if (resultCode == RESULT_OK) {

                File imageDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath(), SharedPref.read("photoFileName", ""));

                Bitmap bitmap = null;

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(imageDirectory));

                    int nh = (int) ( bitmap.getHeight() * (512.0 / bitmap.getWidth()) );
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                    showPhoto.setImageBitmap(scaled);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(this, "Photo saved to SD Card", Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == VIDEO_REQUEST){

            if (resultCode == RESULT_OK) {

                File videoDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath(), SharedPref.read("videoFileName",""));
                Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(videoDirectory.getAbsolutePath(), MediaStore.Video.Thumbnails.MINI_KIND);
                showPhoto.setImageBitmap(thumbnail);
                Toast.makeText(this, "Video saved to SD Card", Toast.LENGTH_SHORT).show();
                SharedPref.write("VIDEO_URI", getIntent().getStringExtra("videoURI"));

            } else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(this, "Video was not taken", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Video was not taken", Toast.LENGTH_SHORT).show();

            }

        }
    }

    private void uploadPhotoPost() throws JSONException {


        final EditText description = (EditText) findViewById(R.id.enterDescriptionEditText);
//        Matcher matcher = HASHTAG_MATCH.matcher(description.getText().toString());
        File environment = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String fileName = SharedPref.read("photoFileName", "");
        final File takenPhoto = new File(environment, fileName);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Uploading. Please wait...");
        pDialog.show();

//        if(matcher.matches()) {

        final String POST_URL = "http://95.85.16.177:3000/api/post";
        JSONObject json = new JSONObject();
        json.put("description", description.getText().toString());
        json.put("content", takenPhoto);

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, POST_URL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(UploadPostActivity.this, "Post Successful", Toast.LENGTH_SHORT).show();
                hidePDialog();
                Intent intent = new Intent(UploadPostActivity.this, PrimaryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UploadPostActivity.this, "There was an error with the request: " + error, Toast.LENGTH_SHORT).show();
                hidePDialog();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("description", description.getText().toString());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                try {
                    params.put("content", new DataPart("post_content.jpg", AppHelper.getFileDataFromFile(getBaseContext(), takenPhoto)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(UploadPostActivity.this, "File not Found", Toast.LENGTH_SHORT).show();
                }
                return params;
            }
        };

        RequestQueueSingleton.getInstance(this).addToRequestQueue(multipartRequest);

    }

    private void uploadVideoPost() throws JSONException {


        final EditText description = (EditText) findViewById(R.id.enterDescriptionEditText);
//        Matcher matcher = HASHTAG_MATCH.matcher(description.getText().toString());
        File environment = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
        final String fileName = SharedPref.read("videoFileName", "");
        final File takenVideo = new File(environment, fileName);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Uploading. Please wait...");
        pDialog.show();

//        if(matcher.matches()) {

         final String POST_URL = "http://95.85.16.177:3000/api/post";
            JSONObject json = new JSONObject();
            json.put("description", description.getText().toString());
            json.put("content", takenVideo);

            VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, POST_URL, new Response.Listener<NetworkResponse>() {
                @Override
                public void onResponse(NetworkResponse response) {
                    hidePDialog();
                    Toast.makeText(UploadPostActivity.this, "Post Successful: " + response, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UploadPostActivity.this, PrimaryActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(UploadPostActivity.this, "There was an error with the request: " + error.toString(), Toast.LENGTH_SHORT).show();
                    hidePDialog();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("description", description.getText().toString());
                    return params;
                }

                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();

                    try {
                        params.put("content", new DataPart("post_content.mp4", AppHelper.getFileDataFromFile(getBaseContext(), takenVideo)));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    return params;
                }
            };

            RequestQueueSingleton.getInstance(this).addToRequestQueue(multipartRequest);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog(){

        if (pDialog != null){
            pDialog.dismiss();
            pDialog = null;
        }

    }

}
