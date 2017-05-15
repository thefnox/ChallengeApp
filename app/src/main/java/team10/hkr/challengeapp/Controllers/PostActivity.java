package team10.hkr.challengeapp.Controllers;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    TextView firstCommentView;
    TextView secondCommentView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CookieHandler.setDefault(sessionManager.getCookieManager());
        setContentView(R.layout.single_post);
        setTheFeed();

        final StyleSpan boldStyle = new StyleSpan(android.graphics.Typeface.BOLD); // Span to make text bold
        final StyleSpan italicStyle = new StyleSpan(android.graphics.Typeface.ITALIC); //Span to make text italic

        firstCommentView = (TextView) findViewById(R.id.username_comment_first);
        secondCommentView = (TextView) findViewById(R.id.username_comment_second);

        String holderUsernameFirst = "Mr.Falafelface";
        String holderUsernameSecond = "SwedishMan";

        String string = holderUsernameFirst + "What an amazingf comment we got here omg its just so greate so good I cant help but noticing..";
        String second_string = holderUsernameSecond + "OMFG dude this is the shaaaayyyttt..";

        SpannableString spannableStringFirst = new SpannableString(string);
        SpannableString spannableStringSecond = new SpannableString(second_string);
        spannableStringFirst.setSpan(boldStyle, 0, holderUsernameFirst.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableStringSecond.setSpan(boldStyle, 0, holderUsernameSecond.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        firstCommentView.setText(spannableStringFirst);
        secondCommentView.setText(spannableStringSecond);
    }

    public void onReportClick(View view) {

        Intent reportIntent = new Intent(this, ReportActivity.class);
        startActivity(reportIntent);

    }

    private void setTheFeed() {

        final String URL = "http://95.85.16.177:3000/api/post/5914cd3b46c5141865ef3340";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("YUPPI", "Response; " + response.toString());
                try {
                    post = new Post(response);
                    for(int i = 0; i < post.getComments().length(); i++) {
                        commentObjects.add(new Comment(post.getComments().getJSONObject(i)));
                        Log.v("YUPPI", String.valueOf(post.getComments().length()) + response.toString());
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

//        CommentListAdapter adapter = new CommentListAdapter(PostActivity.this, commentObjects);
//        commentsListView = (ListView) findViewById(R.id.comment_list_post);
//        commentsListView.setAdapter(adapter);
    }
}

