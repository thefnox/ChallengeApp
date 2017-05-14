package team10.hkr.challengeapp.Controllers;

import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.net.CookieHandler;
import java.util.ArrayList;

import team10.hkr.challengeapp.AppSingleton;
import team10.hkr.challengeapp.Models.Post;
import team10.hkr.challengeapp.Models.User;
import team10.hkr.challengeapp.PostListAdapter;
import team10.hkr.challengeapp.R;

public class ProfileActivity extends AppCompatActivity {

    AppSingleton sessionManager = AppSingleton.getInstance();
    ImageView profilePicture;
    TextView profileUsername;
    TextView profileName;
    ListView postsListView;
    TextView profileDescription; //Not implemented in the server..
    ArrayList<Post> postArrayList = new ArrayList<Post>();
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        CookieHandler.setDefault(sessionManager.getCookieManager());
        serverRequest();
        requestForFeed();
    }

    private void serverRequest(){


        final String URL = "http://95.85.16.177:3000/api/user/me";

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("YUPPI", "Response; " + response.toString());
                try {
                    user = new User(response);
                    profilePicture = (ImageView) findViewById(R.id.photo_profile);
                    profileName = (TextView) findViewById(R.id.name_and_surname_profile);
                    profileUsername = (TextView) findViewById(R.id.username_profile);
                    //profileDescription = (TextView) findViewById(R.id.profile_description);

                    Toast.makeText(ProfileActivity.this, response.getString("username"), Toast.LENGTH_LONG).show();

                    profileName.setText(user.getFirstName() + " " + user.getLastName());
                    profileUsername.setText(user.getUserName());
                    profilePicture.setImageResource(R.drawable.profile_picture_test);

                    //profileDescription.setText(user.getProfileDescription());

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

    }

    private void requestForFeed(){
        final String URL = "http://95.85.16.177:3000/api/user/5914cb9446c5141865ef3338/posts";
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.v("YUPPI", "Response; " + response.toString());

                    for(int i=0; i < response.length(); i++) {
                        postArrayList.add(i, new Post(response.getJSONObject(i)));
                    }
                    postsListView = (ListView) findViewById(R.id.post_list_profile);
                    postsListView.setAdapter(new PostListAdapter(ProfileActivity.this, postArrayList));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("WTF", "Err: " + error.getLocalizedMessage());
            }
        });

        Volley.newRequestQueue(this).add(jsonArrayRequest);

    }

}
