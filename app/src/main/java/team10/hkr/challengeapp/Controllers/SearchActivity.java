package team10.hkr.challengeapp.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.net.CookieHandler;
import java.util.ArrayList;

import team10.hkr.challengeapp.AppSingleton;
import team10.hkr.challengeapp.CustomGridLayoutManager;
import team10.hkr.challengeapp.Models.Post;
import team10.hkr.challengeapp.Models.Tag;
import team10.hkr.challengeapp.PostRecyclerAdapter;
import team10.hkr.challengeapp.R;
import team10.hkr.challengeapp.TagSearchRecyclerListAdapter;

public class SearchActivity extends AppCompatActivity {

    AppSingleton sessionManager = AppSingleton.getInstance();
    ArrayList<Tag> tagArrayList = new ArrayList<Tag>();
    private TagSearchRecyclerListAdapter adapter;
    private RecyclerView popularRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        CookieHandler.setDefault(sessionManager.getCookieManager());
        requestForPopularFeed();

    }
    public void requestForPopularFeed() {
        final String URL = "http://95.85.16.177:3000/api/tags";
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        tagArrayList.add(new Tag(response.getJSONObject(i)));
                        Log.d("tagArrayListSize: ", String.valueOf(tagArrayList.size()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                popularRecyclerView = (RecyclerView) findViewById(R.id.tags_recycler_view_search);
                popularRecyclerView.setLayoutManager(new CustomGridLayoutManager(SearchActivity.this));
                adapter = new TagSearchRecyclerListAdapter(SearchActivity.this, tagArrayList);
                popularRecyclerView.setAdapter(adapter);
                Log.d("postarraylistsize: ", String.valueOf(tagArrayList.size()));

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
