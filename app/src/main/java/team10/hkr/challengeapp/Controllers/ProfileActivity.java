package team10.hkr.challengeapp.Controllers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.Inflater;

import team10.hkr.challengeapp.AppSingleton;
import team10.hkr.challengeapp.CommentListAdapter;
import team10.hkr.challengeapp.Models.Comment;
import team10.hkr.challengeapp.Models.Post;
import team10.hkr.challengeapp.Models.User;
import team10.hkr.challengeapp.PostListAdapter;
import team10.hkr.challengeapp.PostRecyclerAdapter;
import team10.hkr.challengeapp.R;

import static java.security.AccessController.getContext;

public class ProfileActivity extends AppCompatActivity {

    private AppSingleton sessionManager = AppSingleton.getInstance();
    private ImageView profilePicture;
    private TextView profileUsername;
    private TextView profileName;
    private RecyclerView profileRecyclerView;
    private PostRecyclerAdapter adapter;
    private TextView profileDescription; //Not implemented in the server..
    private ArrayList<Post> postArrayList = new ArrayList<Post>();
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        CookieHandler.setDefault(sessionManager.getCookieManager());

        serverRequest();
        requestForFeed();
    }

    private void serverRequest(){

        final String URL = "http://95.85.16.177:3000/api/user/";

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("YUPPI", "Response; " + response.toString());
                try {
                    user = new User(response);
                    sessionManager.setUser(new User(response));
                    profilePicture = (ImageView) findViewById(R.id.photo_profile);
                    profileName = (TextView) findViewById(R.id.name_and_surname_profile);
                    profileUsername = (TextView) findViewById(R.id.username_profile);
                    //profileDescription = (TextView) findViewById(R.id.profile_description);

                    // FILL THE INFO    //  //  //  //  //  //
                    Log.d("PPURL", "http://95.85.16.177:3000" + user.getProfilePictureURL());
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL("http://95.85.16.177:3000" + user.getProfilePictureURL()).getContent());
                        profilePicture.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    profileName.setText(String.valueOf(user.getFirstName() + " " + user.getLastName()));
                    profileUsername.setText(user.getUserName());
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
        final String URL = "http://95.85.16.177:3000/api/user/" + sessionManager.getUser().getUUID() + "/posts";
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i=response.length()-1; i >= 0; i--) {
                        postArrayList.add(new Post(response.getJSONObject(i)));
                    }
                    Log.v("YUPPI", "Response; ##requestfeed profile## " + response.length() + " " + postArrayList.size() + " - >> "
                            + postArrayList.get(0).getDescription() + response.toString() + " " + postArrayList.get(0).getComments().length() + " " + postArrayList.get(1).getComments().length());

                    profileRecyclerView = (RecyclerView) findViewById(R.id.profile_feed_recycler);
                    profileRecyclerView.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));
                    adapter = new PostRecyclerAdapter(ProfileActivity.this, postArrayList);
                    profileRecyclerView.setAdapter(adapter);

                    Log.d("profilepostarraysize: ", String.valueOf(postArrayList.size()));

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
