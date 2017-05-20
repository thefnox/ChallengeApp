package team10.hkr.challengeapp.Controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import team10.hkr.challengeapp.AppSingleton;
import team10.hkr.challengeapp.CommentListAdapter;
import team10.hkr.challengeapp.Controllers.ProfileActivity;
import team10.hkr.challengeapp.Models.Comment;
import team10.hkr.challengeapp.Models.User;
import team10.hkr.challengeapp.R;
import team10.hkr.challengeapp.RequestQueueSingleton;
import team10.hkr.challengeapp.SharedPref;

public class CommentActivity extends AppCompatActivity {

    private ListView commentListView;
    private String data;
    private ArrayList<Comment> commentArray = new ArrayList<>();
    private ImageView approvalButton;
    private EditText commentEditText;
    AppSingleton sessionManager = AppSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        CookieHandler.setDefault(sessionManager.getCookieManager());


        commentListView = (ListView) findViewById(R.id.comment_list_view_comment_activity);
        approvalButton = (ImageView) findViewById(R.id.comment_approve);
        commentEditText = (EditText) findViewById(R.id.edit_text_comment_activity);



        //get the info passed with the intent
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

        RequestQueueSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);

        //Adding a comment with the button ad the editText
        final String COMMENT_KEY = "comment";
        final String POST_COMMENT_URL = "http://95.85.16.177:3000/api/post/" + data + "/comment";
        approvalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, POST_COMMENT_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(CommentActivity.this, "Success", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("WTF", "Response; " + error.toString());
                        Toast.makeText(CommentActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();
                        params.put(COMMENT_KEY, commentEditText.getText().toString());
                        return params;
                    }
                };
                RequestQueueSingleton.getInstance(getBaseContext()).addToRequestQueue(stringRequest);
                commentEditText.setText("");
            }
        });







        commentListView.setAdapter(new CommentListAdapter(this, commentArray));
    }
}
