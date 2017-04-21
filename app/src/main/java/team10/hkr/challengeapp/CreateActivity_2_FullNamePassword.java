package team10.hkr.challengeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateActivity_2_FullNamePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_2__full_name_password);
    }

    public void onContinueClick(View view) {
        //changing the activity with the button
        Intent myIntent = new Intent(CreateActivity_2_FullNamePassword.this, CreateActivity_3_UserName.class);
        startActivity(myIntent);

        //Add more action here

    }

}
