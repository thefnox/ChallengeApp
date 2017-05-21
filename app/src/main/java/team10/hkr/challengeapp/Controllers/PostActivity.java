package team10.hkr.challengeapp.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import team10.hkr.challengeapp.AppSingleton;
import team10.hkr.challengeapp.CommentListAdapter;
import team10.hkr.challengeapp.CustomListAdapter;
import team10.hkr.challengeapp.Models.Comment;
import team10.hkr.challengeapp.Models.Pokemon;
import team10.hkr.challengeapp.Models.Post;
import team10.hkr.challengeapp.Models.User;
import team10.hkr.challengeapp.R;
import team10.hkr.challengeapp.RequestQueueSingleton;

import static android.R.attr.id;
import static android.R.attr.tag;

public class PostActivity extends Activity {

    AppSingleton sessionManager = AppSingleton.getInstance();
    private TextView authorName;
    private CircleImageView authorPhoto;
    private TextView tags;
    private TextView description;
    private ImageView content;
    private ImageButton likeButton;
    private ImageButton commentButton;
    private ImageButton shareButton;
    private ImageButton flagButton;
    private TextView likesCount;
    private TextView followButton;
    int i = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CookieHandler.setDefault(sessionManager.getCookieManager());
        setContentView(R.layout.single_post);
        authorName = (TextView) findViewById(R.id.username_post);
        authorPhoto = (CircleImageView) findViewById(R.id.profile_photo_post);
        tags = (TextView) findViewById(R.id.challenge_tag_post);
        description = (TextView) findViewById(R.id.description_post);
        content = (ImageView) findViewById(R.id.content_view_post);
        likeButton = (ImageButton) findViewById(R.id.thumbsup_post);
        commentButton = (ImageButton) findViewById(R.id.comment_post);
        shareButton = (ImageButton) findViewById(R.id.share_post);
        flagButton = (ImageButton) findViewById(R.id.flag_post);
        likesCount = (TextView) findViewById(R.id.likes_post);
        followButton = (TextView) findViewById(R.id.follow_button_post);
        boolean isLiked = getIntent().getExtras().getBoolean("isLiked");

        if(getIntent().getExtras().getBoolean("isLiked")) {
            likeButton.setBackgroundColor(0xffffbb33);

        } else
            likeButton.setBackgroundColor(0x00000000);


        try {
            Log.d("URL1", getIntent().getStringExtra("profileImageURL"));
            Log.d("URL2", getIntent().getStringExtra("contentURL"));
            Bitmap bitmapUserPhoto = BitmapFactory.decodeStream((InputStream) new URL("http://95.85.16.177:3000" + getIntent().getStringExtra("profileImageURL")).getContent());
            Bitmap bitmapContent = BitmapFactory.decodeStream((InputStream) new URL(getIntent().getStringExtra("contentURL")).getContent());
            authorPhoto.setImageBitmap(bitmapUserPhoto);
            content.setVisibility(View.VISIBLE);
            content.setImageBitmap(bitmapContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        authorName.setText(getIntent().getStringExtra("username"));
        likesCount.setText(String.valueOf(getIntent().getStringExtra("likesCount") + " Likes"));
        tags.setText(getIntent().getStringExtra("tags"));
        description.setText(getIntent().getStringExtra("description"));

        if (getIntent().getExtras().getBoolean("isFollowingBool")) {
            followButton.setText(String.valueOf("Unfollow"));
        }

        //Click Listeners
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(PostActivity.this, CommentActivity.class);
                mIntent.putExtra("post_id", getIntent().getStringExtra("UUID"));
                startActivity(mIntent);
            }
        });

        flagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(PostActivity.this, ReportActivity.class);
                mIntent.putExtra("post_id", getIntent().getStringExtra("UUID"));
                mIntent.putExtra("username", getIntent().getStringExtra("username"));
                mIntent.putExtra("contentURL", getIntent().getStringExtra("contentURL"));
                mIntent.putExtra("tags", getIntent().getStringExtra("tags"));
                startActivity(mIntent);
            }
        });

        if (getIntent().getStringExtra("authorUUID").equals(sessionManager.getUser().getUUID())) {
            followButton.setVisibility(View.GONE);
        } else {
            //follow-unfollow logic here.
        }
        Log.d("HereC", "1Ami");

        if(getIntent().getStringArrayListExtra("likesUsers").contains(sessionManager.getUser().getUUID())) {
            likeButton.setBackgroundColor(0xffffbb33);
        } else {
            likeButton.setBackgroundColor(0x00000000);
        }

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User you = AppSingleton.getInstance().getUser();
                Log.d("HereC", "2Ami");
                if ((i%2)==0) {
                    likeButton.setBackgroundColor(0xffffbb33);
                    Log.d("HereC", "Red");
                    i++;
                } else {
                    likeButton.setBackgroundColor(0x00000000);
                    if(i%2 != 0) {
                        Log.d("HereC", "White");
                        i++;
                    } else {
                        i++;
                        i++;
                    }
                }

                if (getIntent().getStringExtra("authorUUID").equals(you.getUUID())) {
                    Toast.makeText(PostActivity.this, "You cannot like your own posts", Toast.LENGTH_SHORT).show();

                } else {

                    final String URL = "http://95.85.16.177:3000/api/post/" + getIntent().getStringExtra("UUID") + "/like";

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                        //This needs to be fixed!
                        @Override
                        public void onResponse(String response) {
                            //!getIntent().getStringArrayListExtra("likesUsers").contains(sessionManager.getUser().getUUID())
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(PostActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }

                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("post_id", getIntent().getStringExtra("UUID"));
                            return params;
                        }
                    };
                    RequestQueueSingleton.getInstance(PostActivity.this).addToRequestQueue(stringRequest);
                }
            }
        });
    }
}


    //Saving this if we will ever need to have an actual request for the post
//    private void setThePost(String UUID) {
//
//        final String URL = "http://95.85.16.177:3000/api/post/" + UUID;
//        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.v("YUPPI", "Response; " + response.toString());
//                try {
//                    post = new Post(response);
//                    for(int i = 0; i < post.getComments().length(); i++) {
//                        commentObjects.add(new Comment(post.getComments().getJSONObject(i)));
//                        Log.v("YUPPI", String.valueOf(post.getComments().length()) + response.toString());
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.v("WTF", "Err: " + error.getLocalizedMessage());
//            }
//        });
//        Volley.newRequestQueue(this).add(jsonObjectRequest);
//
//    }
