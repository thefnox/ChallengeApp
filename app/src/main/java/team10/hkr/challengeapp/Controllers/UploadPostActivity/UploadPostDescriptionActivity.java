package team10.hkr.challengeapp.Controllers.UploadPostActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import team10.hkr.challengeapp.Controllers.PrimaryActivity;
import team10.hkr.challengeapp.R;
import team10.hkr.challengeapp.RequestQueueSingleton;
import team10.hkr.challengeapp.SharedPref;

public class UploadPostDescriptionActivity extends AppCompatActivity {

    ImageView photo;
    private static final Pattern HASHTAG_MATCH =
            Pattern.compile("(?:^|\\s|[\\p{Punct}&&[^/]])(#[\\p{L}0-9-_]+)");


    static final int CAM_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_post_description);

        final Button takePhotoBtn = (Button) findViewById(R.id.takePhotoButton);
        Button uploadBtn = (Button) findViewById(R.id.uploadButton);

        takePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoButton();
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void takePhotoButton(){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String fileName = getFileName();

        SharedPref.write("photoFileName", fileName);

        File takenPhoto = new File(externalStoragePublicDirectory, fileName);
        Uri pictureUri = Uri.fromFile(takenPhoto);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);

        startActivityForResult(intent, CAM_REQUEST);
    }

    private String getFileName() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmSS");
        String timestamp = sdf.format(new Date());
        String fileName = "Challenge App " + timestamp + ".jpg";
        return fileName;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAM_REQUEST) {

            if (resultCode == RESULT_OK) {

                Toast.makeText(this, "Photo saved to SD Card", Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadPost() throws JSONException {


        EditText description = (EditText) findViewById(R.id.enterDescriptionEditText);
        Matcher matcher = HASHTAG_MATCH.matcher(description.getText().toString());
        File environment = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String fileName = SharedPref.read("photoFileName", "");
        File takenPhoto = new File(environment, fileName);

        if(matcher.matches()) {

            final String POST_URL = "http://95.85.16.177:3000/api/post";
            JSONObject json = new JSONObject();
            json.put("description", description.getText().toString());
            json.put("content", takenPhoto);


// THIS CODE IS NOT WORKING

//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, POST_URL, json,
//                    new Response.Listener<JSONObject>() {
//
//                        @Override
//                        public void onResponse(JSONObject response) {
//
//                            Toast.makeText(UploadPostDescriptionActivity.this, "Post Successful", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(UploadPostDescriptionActivity.this, PrimaryActivity.class);
//                            startActivity(intent);
//                        }
//
//                    }, new Response.ErrorListener() {
//
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(UploadPostDescriptionActivity.this,
//                                        "Something went wrong", Toast.LENGTH_SHORT).show();
//                            }
//                    });
//
//            RequestQueueSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

        } else {

            Toast.makeText(this, "Description must have between 1 and 5 hashtags.", Toast.LENGTH_SHORT).show();

        }

    }

}
