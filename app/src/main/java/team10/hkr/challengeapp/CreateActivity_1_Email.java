package team10.hkr.challengeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateActivity_1_Email extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_1_email);
    }

    public void onContinueClick(View view) {
        //changing the activity with the button
        Intent myIntent = new Intent(CreateActivity_1_Email.this, CreateActivity_2_FullNamePassword.class);
        startActivity(myIntent);

        //Add more action here

    }

}
