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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import team10.hkr.challengeapp.Models.Pokemon;
import team10.hkr.challengeapp.Models.Post;
import team10.hkr.challengeapp.R;

import static android.R.attr.id;

public class PostActivity extends AppCompatActivity {

    Pokemon bulba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);



        final String url = "http://pokeapi.co/api/v2/pokemon/1";
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("YUPPI", "Response; " + response.toString());
                try {
                    bulba = new Pokemon(response);
                    System.out.println(bulba.getName());
                    System.out.println(bulba.getId());
                    System.out.println(bulba.getHeight());
                    System.out.println(bulba.getWeight());
                    System.out.println(bulba.getOrder());
                    System.out.println(bulba.getBase_experience());
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

        String[] comments = {                                                                                                                   //
                "Hello there!",                                                                                                                 //
                "Hello there to you too sir!",                                                                                                  //
                "What a fascinating challenge you got there!"
        };

        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, comments);
        ListView listView = (ListView) findViewById(R.id.comment_list);
        listView.setAdapter(itemsAdapter);

        Button reportButton = (Button) findViewById(R.id.reportButton);
                                                                                                                      //


//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.post_layout);
//
//        ImageView imageView = new ImageView(getApplicationContext());
//        imageView.setLayoutParams(new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//        ));
//        //Instead of this we gonna get the source from the post object
//        imageView.setImageDrawable(getResources().getDrawable(R.drawable.medal_with_shadow));
    }

    public void onReportClick(View view) {

        Intent reportIntent = new Intent(this, ReportActivity.class);
        startActivity(reportIntent);

    }
}
