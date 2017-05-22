package team10.hkr.challengeapp.Controllers.SettingsActivity;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import team10.hkr.challengeapp.AppSingleton;
import team10.hkr.challengeapp.Controllers.PrimaryActivity;
import team10.hkr.challengeapp.MultipartRequestLibraryCode.AppHelper;
import team10.hkr.challengeapp.MultipartRequestLibraryCode.VolleyMultipartRequest;
import team10.hkr.challengeapp.R;
import team10.hkr.challengeapp.RequestQueueSingleton;
import team10.hkr.challengeapp.SharedPref;

public class ChangeProfilePictureActivity extends Activity {

    static final int CAM_REQUEST = 1;
    private final int PICK_IMAGE_REQUEST = 2;
    private NetworkImageView mNetworkImageView;
    private ImageLoader mImageLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile_picture);

        setCurrentProfilePicture();

        Button cameraButton = (Button) findViewById(R.id.cameraButton);
        Button galleryButton = (Button) findViewById(R.id.galleryButton);
        Button uploadNewPicture = (Button) findViewById(R.id.finalChangePictureButton);

        cameraButton.setOnClickListener(new View.OnClickListener() {
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

        uploadNewPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    changePicture();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setCurrentProfilePicture() {

        mNetworkImageView = (NetworkImageView) findViewById(R.id.currentProfilePictureNetwork);

        mImageLoader = RequestQueueSingleton.getInstance(this.getApplicationContext())
                .getImageLoader();

        final String URL = "http://95.85.16.177:3000/" + AppSingleton.getInstance().getUser().getProfilePictureURL();

        mImageLoader.get(URL, ImageLoader.getImageListener(mNetworkImageView,
                R.drawable.profile_photo_logo, android.R.drawable
                        .ic_dialog_alert));

        mNetworkImageView.setImageUrl(URL, mImageLoader);
    }

    public void takePhotoButton(){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String fileName = getFileName();

        SharedPref.write("newProfilePicture", fileName);

        File takenPhoto = new File(externalStoragePublicDirectory, fileName);
        Uri pictureUri = Uri.fromFile(takenPhoto);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);

        startActivityForResult(intent, CAM_REQUEST);
    }

    private String getFileName() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmSS");
        String timestamp = sdf.format(new Date());
        return "ProfilePicture" + timestamp + ".jpg";
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        TextView pictureDirectory = (TextView) findViewById(R.id.newPicturePath);

        if (requestCode == CAM_REQUEST) {

            if (resultCode == RESULT_OK) {

                ImageView newProfilePicture = (ImageView) findViewById(R.id.newProfilePictureImageView);
                File imageDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + SharedPref.read("newProfilePicture", ""));
                pictureDirectory.setText(imageDirectory.toString());
                newProfilePicture.setImageBitmap(BitmapFactory.decodeFile(imageDirectory.toString()));

                Toast.makeText(this, "Photo saved to SD Card", Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
            }

        }

        else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            File picturePath = new File(getPath(this.getApplicationContext(), uri));

            ImageView newProfilePicture = (ImageView) findViewById(R.id.newProfilePictureImageView);

            pictureDirectory.setText(picturePath.toString());
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

        final TextView pictureDirectory= (TextView) findViewById(R.id.newPicturePath);
        final ImageView newProfilePicture = (ImageView) findViewById(R.id.newProfilePictureImageView);

        final String POST_URL = "http://95.85.16.177:3000/api/user/picture";
        JSONObject json = new JSONObject();
        json.put("newProfilePicture", pictureDirectory);

        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, POST_URL, new Response.Listener<NetworkResponse>() {

            @Override
            public void onResponse(NetworkResponse response) {
                Toast.makeText(ChangeProfilePictureActivity.this, "Profile Picture Successfully Changed", Toast.LENGTH_SHORT).show();
                AppSingleton.getInstance().updateUser(getBaseContext());
                Intent intent = new Intent(ChangeProfilePictureActivity.this, PrimaryActivity.class);
                startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChangeProfilePictureActivity.this, "There was an error with the request.", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, DataPart> getByteData() {

                Map<String, DataPart> params = new HashMap<>();
                params.put("newProfilePicture", new DataPart("profile_picture.jpg", AppHelper.getFileDataFromDrawable(getBaseContext(), newProfilePicture.getDrawable()), "image/jpeg"));

                return params;
            }
        };

        RequestQueueSingleton.getInstance(this).addToRequestQueue(multipartRequest);

    }

    // THE FOLLOWING IS LIBRARY CODE TO CONVERT THE URI RECEIVED FROM GALLERY SELECTION INTO AN
    // ABSOLUTE FILE PATH

    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @author paulburke
     */
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


}
