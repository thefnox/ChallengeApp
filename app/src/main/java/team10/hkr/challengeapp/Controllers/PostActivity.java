package team10.hkr.challengeapp.Controllers;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import team10.hkr.challengeapp.AppSingleton;
import team10.hkr.challengeapp.CommentListAdapter;
import team10.hkr.challengeapp.CustomListAdapter;
import team10.hkr.challengeapp.Models.Comment;
import team10.hkr.challengeapp.Models.Pokemon;
import team10.hkr.challengeapp.Models.Post;
import team10.hkr.challengeapp.Models.User;
import team10.hkr.challengeapp.R;

import static android.R.attr.id;

public class PostActivity extends AppCompatActivity {

    AppSingleton sessionManager = AppSingleton.getInstance();
    Post post;
    ArrayList<Comment> commentObjects = new ArrayList<Comment>();
    ListView commentsListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CookieHandler.setDefault(sessionManager.getCookieManager());
        setContentView(R.layout.single_post);
        setTheFeed();
    }

    public void onReportClick(View view) {

        Intent reportIntent = new Intent(this, ReportActivity.class);
        startActivity(reportIntent);

    }

    private void setTheFeed() {

        final String URL = "95.85.16.177:3000/api/post/5914cd3b46c5141865ef3340";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("YUPPI", "Response; " + response.toString());
                try {
                    post = new Post(response);
                    for(int i = 0; i < post.getComments().length(); i++) {
                        commentObjects.add(i, new Comment(post.getComments().getJSONObject(i)));
                    }
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
        Volley.newRequestQueue(this).add(jsonObjectRequest);

        CommentListAdapter adapter = new CommentListAdapter(PostActivity.this, commentObjects);
        commentsListView = (ListView) findViewById(R.id.comment_list_post);
        commentsListView.setAdapter(adapter);
    }
}

