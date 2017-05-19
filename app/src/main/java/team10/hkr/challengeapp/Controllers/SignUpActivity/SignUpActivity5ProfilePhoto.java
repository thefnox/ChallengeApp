package team10.hkr.challengeapp.Controllers.SignUpActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.CookieHandler;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import team10.hkr.challengeapp.AppSingleton;
import team10.hkr.challengeapp.Controllers.PrimaryActivity;
import team10.hkr.challengeapp.Controllers.SettingsActivity.ChangeProfilePictureActivity;
import team10.hkr.challengeapp.MultipartRequestLibraryCode.AppHelper;
import team10.hkr.challengeapp.MultipartRequestLibraryCode.VolleyMultipartRequest;
import team10.hkr.challengeapp.R;

import team10.hkr.challengeapp.R;
import team10.hkr.challengeapp.RequestQueueSingleton;
import team10.hkr.challengeapp.SharedPref;

import static team10.hkr.challengeapp.Controllers.SettingsActivity.ChangeProfilePictureActivity.getPath;

public class SignUpActivity5ProfilePhoto extends AppCompatActivity {

    AppSingleton sessionManager = AppSingleton.getInstance();
    static final int CAM_REQUEST = 1;
    private final int PICK_IMAGE_REQUEST = 2;
    private NetworkImageView mNetworkImageView;
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_5_profile_photo);
        CookieHandler.setDefault(sessionManager.getCookieManager());

        signIn();

        Button photoButton = (Button) findViewById(R.id.takePhotoAndUploadButton);
        Button galleryButton = (Button) findViewById(R.id.uploadExistingPhotoButton);

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhotoButton();
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGallery();
            }
        });

    }

    public void takePhotoButton(){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String fileName = getFileName();

        SharedPref.write("createProfilePictureFileName", fileName);

        File takenPhoto = new File(externalStoragePublicDirectory, fileName);
        Uri pictureUri = Uri.fromFile(takenPhoto);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);

        startActivityForResult(intent, CAM_REQUEST);
    }

    public void onFinishClick(View view) throws JSONException {

        changePicture();

    }

    private String getFileName() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmSS");
        String timestamp = sdf.format(new Date());
        return "ProfilePicture" + timestamp + ".jpg";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAM_REQUEST) {

            final ImageView newProfilePicture = (ImageView) findViewById(R.id.createProfilePictureImageView);


            if (resultCode == RESULT_OK) {


                File imageDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath(), SharedPref.read("createProfilePictureFileName", ""));
                SharedPref.write("setProfilePicturePath", imageDirectory.getAbsolutePath());
                Bitmap bitmap = null;

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(imageDirectory));

                    int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
                    Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 512, nh, true);
                    newProfilePicture.setImageBitmap(scaled);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Toast.makeText(this, "Photo saved to SD Card", Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            File picturePath = new File(getPath(this.getApplicationContext(), uri));

            SharedPref.write("setProfilePicturePath", picturePath.toString());
            ImageView newProfilePicture = (ImageView) findViewById(R.id.createProfilePictureImageView);

            newProfilePicture.setImageBitmap(BitmapFactory.decodeFile(picturePath.toString()));

        }
    }

    private void goToGallery() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);


    }

    private void changePicture() throws JSONException {

        final File picturePath = new File(SharedPref.read("setProfilePicturePath", ""));
        final String POST_URL = "http://95.85.16.177:3000/api/user/picture";
        JSONObject json = new JSONObject();
        json.put("newProfilePicture", picturePath);

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, POST_URL, new Response.Listener<NetworkResponse>() {

            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(SignUpActivity5ProfilePhoto.this, "Profile Picture Successfully Changed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity5ProfilePhoto.this, PrimaryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUpActivity5ProfilePhoto.this, "There was an error with the request: " + error, Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, DataPart> getByteData() {

                Map<String, DataPart> params = new HashMap<>();
                try {
                    params.put("newProfilePicture", new DataPart("profile_picture.jpg", AppHelper.getFileDataFromFile(getBaseContext(),picturePath)));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                return params;
            }
        };

        RequestQueueSingleton.getInstance(this).addToRequestQueue(multipartRequest);

    }

    public void signIn(){

        final String USERNAME_OR_EMAIL_KEY = "usernameOrEmail";
        final String PASSWORD_KEY = "password";
        final String SIGNIN_URL = "http://95.85.16.177:3000/api/auth/signin";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SIGNIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SignUpActivity5ProfilePhoto.this, "Signed in as " +
                        getIntent().getStringExtra("username"), Toast.LENGTH_SHORT).show();

//                Intent i = new Intent(SignUpActivity5ProfilePhoto.this, PrimaryActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(i);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUpActivity5ProfilePhoto.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put(USERNAME_OR_EMAIL_KEY, getIntent().getStringExtra("username"));
                params.put(PASSWORD_KEY, getIntent().getStringExtra("password"));
                return params;
            }
        };

        RequestQueueSingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}

