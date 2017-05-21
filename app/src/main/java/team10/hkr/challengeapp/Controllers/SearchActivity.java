package team10.hkr.challengeapp.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

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
import team10.hkr.challengeapp.Models.SearchTag;
import team10.hkr.challengeapp.Models.Tag;
import team10.hkr.challengeapp.PostRecyclerAdapter;
import team10.hkr.challengeapp.R;
import team10.hkr.challengeapp.TagSearchRecyclerListAdapter;

public class SearchActivity extends Activity {

    AppSingleton sessionManager = AppSingleton.getInstance();
    private ArrayList<SearchTag> tagArrayList = new ArrayList<SearchTag>();
    private TagSearchRecyclerListAdapter adapter;

    private RecyclerView popularRecyclerView;
    private ImageButton searchButton;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        CookieHandler.setDefault(sessionManager.getCookieManager());
        popularRecyclerView = (RecyclerView) findViewById(R.id.tags_recycler_view_search);

        searchButton = (ImageButton) findViewById(R.id.search_button_search_activity);
        searchEditText = (EditText) findViewById(R.id.search_edit_text);

        //Start Up Logic
        requestForPopularFeed();
        //Search Made Logic
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!String.valueOf(searchEditText.getText()).equals("")) {
                    Intent mIntent = new Intent(SearchActivity.this, SearchResultActivity.class);
                    mIntent.putExtra("KEY_WORD", String.valueOf(searchEditText.getText()));
                    startActivity(mIntent);
                }
            }
        });
    }

    public void requestForPopularFeed() {
        final String URL = "http://95.85.16.177:3000/api/tags";
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        tagArrayList.add(new SearchTag(response.getJSONObject(i)));
                        Log.d("tagArrayListSize: ", String.valueOf(tagArrayList.size()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
