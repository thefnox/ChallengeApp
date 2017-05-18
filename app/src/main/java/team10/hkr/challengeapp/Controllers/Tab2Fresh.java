package team10.hkr.challengeapp.Controllers;

import android.app.Dialog;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

import team10.hkr.challengeapp.Models.Post;
import team10.hkr.challengeapp.PostListAdapter;
import team10.hkr.challengeapp.PostRecyclerAdapter;
import team10.hkr.challengeapp.R;

/**
 * Created by Charlie on 25.04.2017.
 */

public class Tab2Fresh extends Fragment {

    private RecyclerView freshPostsRecyclerView;
    private PostRecyclerAdapter adapter;
    private View view;
    private ArrayList<Post> postArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab2_primary_fresh, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        requestForFeed();
    }

    private void requestForFeed() {
        final String URL = "http://95.85.16.177:3000/api/post/newest";
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        postArrayList.add(new Post(response.getJSONObject(i)));
                        Log.d("postarraylistsize2: ", String.valueOf(postArrayList.size()));
                    }
                    Log.v("YUPPI", "Response; " + " FIND ME CHARLIE " + postArrayList.size() + " - >> "
                            + postArrayList.get(0).getDescription() + response.toString() + " " + postArrayList.get(0).getComments().length() + " " + postArrayList.get(1).getComments().length());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                freshPostsRecyclerView = (RecyclerView) view.findViewById(R.id.fresh_content_recycler_view);
                freshPostsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new PostRecyclerAdapter(getActivity(), postArrayList);
                freshPostsRecyclerView.setAdapter(adapter);
                Log.d("postarraylistsize: ", String.valueOf(postArrayList.size()));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("WTF", "Err: " + error.getLocalizedMessage());
            }
        });

        Volley.newRequestQueue(getActivity()).add(jsonArrayRequest);
    }
}
