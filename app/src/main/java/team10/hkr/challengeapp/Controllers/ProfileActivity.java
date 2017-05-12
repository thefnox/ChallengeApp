package team10.hkr.challengeapp.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import team10.hkr.challengeapp.Models.Pokemon;
import team10.hkr.challengeapp.Models.User;
import team10.hkr.challengeapp.R;

public class ProfileActivity extends AppCompatActivity {


    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fillProfileInfo();

    }

    private void fillProfileInfo(){

        ImageView profilePicture = (ImageView) findViewById(R.id.profilePicture);
        final TextView profileName = (TextView) findViewById(R.id.profileName);
        TextView profileCity = (TextView) findViewById(R.id.profileCity);
        TextView profileCountry = (TextView) findViewById(R.id.profileCountry);
        TextView profileStars = (TextView) findViewById(R.id.profileStars);
        TextView profileChampions = (TextView) findViewById(R.id.profileChampions);
        TextView profileFacebook = (TextView) findViewById(R.id.profileFacebook);
        TextView profileTwitter = (TextView) findViewById(R.id.profileTwitter);
        TextView profileDescription = (TextView) findViewById(R.id.profileDescription);


        final String url = "http://95.85.16.177:3000/api/user/me";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("YUPPI", "Response; " + response.toString());
                try {
                    user = new User(response);
                    Toast.makeText(ProfileActivity.this, response.getString("name"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("WTF", "Err: " + error.getLocalizedMessage());
            }
        });

        Volley.newRequestQueue(this).add(jsonObjectRequest);

        profilePicture.setImageResource(R.drawable.profile_picture_test);
        if( user != null) {
            profileName.setText(user.getCity());
        }
    }



}
