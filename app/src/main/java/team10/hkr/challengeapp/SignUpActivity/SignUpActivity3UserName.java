package team10.hkr.challengeapp.SignUpActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import team10.hkr.challengeapp.R;

public class SignUpActivity3UserName extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_3_user_name);
    }

    public void onContinueClick(View view) {
        //changing the activity with the button
        Intent myIntent = new Intent(SignUpActivity3UserName.this, SignUpActivity5ProfilePhoto.class);
        startActivity(myIntent);

        //Add more action here

    }
}
