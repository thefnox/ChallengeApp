package team10.hkr.challengeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateActivity_3_UserName extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_3_user_name);
    }

    public void onContinueClick(View view) {
        //changing the activity with the button
        Intent myIntent = new Intent(CreateActivity_3_UserName.this, CreateActivity_5_ProfilePhoto.class);
        startActivity(myIntent);

        //Add more action here

    }
}
