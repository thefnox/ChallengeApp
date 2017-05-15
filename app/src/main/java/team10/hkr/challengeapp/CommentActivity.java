package team10.hkr.challengeapp;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import team10.hkr.challengeapp.Controllers.ProfileActivity;
import team10.hkr.challengeapp.Models.Comment;
import team10.hkr.challengeapp.Models.User;

public class CommentActivity extends AppCompatActivity {

    private ListView commentListView;
    private String data;
    private ArrayList<Comment> commentArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        commentListView = (ListView) findViewById(R.id.comment_list_view_comment_activity);
        Bundle extras = getIntent().getExtras();
        if(extras != null) data = extras.getString("post_id");

        final String URL = "http://95.85.16.177:3000/api/post/" + data + "/";

        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("YUPPI", "Response; " + response.toString());
                try {
                    for(int i = 0; i < response.getJSONArray("comments").length(); i++) {
                        commentArray.add(new Comment(response.getJSONArray("comments").getJSONObject(i)));
                    }
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

        commentListView.setAdapter(new CommentListAdapter(this, commentArray));
    }
}
