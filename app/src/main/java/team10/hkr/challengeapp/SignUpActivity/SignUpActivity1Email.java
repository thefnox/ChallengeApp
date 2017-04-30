package team10.hkr.challengeapp.SignUpActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import team10.hkr.challengeapp.R;

public class SignUpActivity1Email extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_1_email);
    }

    public void onContinueClick(View view) {
        //changing the activity with the button
        Intent myIntent = new Intent(SignUpActivity1Email.this, SignUpActivity2FullNamePassword.class);
        startActivity(myIntent);

        //Add more action here

    }

}
