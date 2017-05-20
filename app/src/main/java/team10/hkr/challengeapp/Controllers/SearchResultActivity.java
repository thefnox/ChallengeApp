package team10.hkr.challengeapp.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

import team10.hkr.challengeapp.Models.Post;
import team10.hkr.challengeapp.PostRecyclerAdapter;
import team10.hkr.challengeapp.R;

public class SearchResultActivity extends AppCompatActivity {

    private ArrayList<Post> postArrayList = new ArrayList<Post>();
    private PostRecyclerAdapter resultAdapter;
    private RecyclerView searchResultRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        searchResultRecyclerView = (RecyclerView) findViewById(R.id.search_result_recycler_view_search);

        requestForSearchFeed(getIntent().getStringExtra("KEY_WORD"));

    }

    private void requestForSearchFeed(String searchWord) {

        final String URL = "http://95.85.16.177:3000/api/search/tag?s=" + searchWord;
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        postArrayList.add(new Post(response.getJSONObject(i)));
                        Log.d("WinningSize: ", String.valueOf(postArrayList.size()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                searchResultRecyclerView.setLayoutManager(new LinearLayoutManager(SearchResultActivity.this));
                resultAdapter = new PostRecyclerAdapter(SearchResultActivity.this, postArrayList);
                searchResultRecyclerView.setAdapter(resultAdapter);
                Log.d("postarraylistsize: ", String.valueOf(postArrayList.size()));

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
