package team10.hkr.challengeapp.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import team10.hkr.challengeapp.Models.User;
import team10.hkr.challengeapp.R;

public class ProfileActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fillProfileInfo();

    }

    private void fillProfileInfo(){

        User user = new User("00001a5", "TestBoy123", "Marliendo Valerssoynh",
                "test@test.com", "hello", "facebook.com/testtesttest", "@testboy", "This is a test profile",
                "Kristianstad", "Sweden", 0, 0, 0, 0, false);

        ImageView profilePicture = (ImageView) findViewById(R.id.profilePicture);
        TextView profileName = (TextView) findViewById(R.id.profileName);
        TextView profileCity = (TextView) findViewById(R.id.profileCity);
        TextView profileCountry = (TextView) findViewById(R.id.profileCountry);
        TextView profileStars = (TextView) findViewById(R.id.profileStars);
        TextView profileChampions = (TextView) findViewById(R.id.profileChampions);
        TextView profileFacebook = (TextView) findViewById(R.id.profileFacebook);
        TextView profileTwitter = (TextView) findViewById(R.id.profileTwitter);
        TextView profileDescription = (TextView) findViewById(R.id.profileDescription);


        profilePicture.setImageResource(R.drawable.profile_picture_test);

        profileName.setText(user.getRealName());
        profileStars.setText(String.valueOf(user.getStars())+ getString(R.string.stars));
        profileCity.setText(user.getCity());
        profileCountry.setText(user.getCountry());
        profileChampions.setText(String.valueOf(user.getChampions())+ getString(R.string.champions));
        profileFacebook.setText(user.getFacebookLink());
        profileTwitter.setText(user.getTwitterHandle());
        profileDescription.setText(user.getProfileDescription());


    }



}
