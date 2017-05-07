package team10.hkr.challengeapp.Controllers.UploadPostActivity;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import team10.hkr.challengeapp.R;

public class CameraActivity extends Activity {

    Button takePhotoButton;
    Button takeVideoButton;
    private static final int CAM_REQUEST = 1313;
    private static final int REQUEST_VIDEO_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        takePhotoButton = (Button) findViewById(R.id.takePhotoButton);
        takeVideoButton = (Button) findViewById(R.id.takeVideoButton);

        takeVideoButton.setOnClickListener(new takeVideoButtonClicker());
        takePhotoButton.setOnClickListener(new takePhotoButtonClicker());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);

    }

    private class takePhotoButtonClicker implements Button.OnClickListener{

        @Override
        public void onClick(View view){
            Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(photoIntent, CAM_REQUEST);
        }

    }

    private class takeVideoButtonClicker implements Button.OnClickListener{


        @Override
        public void onClick(View v) {

            Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            startActivityForResult(videoIntent, REQUEST_VIDEO_CAPTURE);

        }
    }


}
