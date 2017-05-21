package team10.hkr.challengeapp;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieManager;
import java.util.ArrayList;

import team10.hkr.challengeapp.Controllers.PrimaryActivity;
import team10.hkr.challengeapp.Models.User;

/**
 * Created by Charlie on 13.05.2017.
 */

public class AppSingleton {

    private static AppSingleton session;
    private CookieManager manager = new CookieManager();
    private User user;
    private User updatedUser;
    private Context context;
    private ArrayList<String> followingUsers = new ArrayList<>();

    private AppSingleton() {

    }
    public static AppSingleton getInstance() {
        if(session == null) {
            session = new AppSingleton();
        }
        return session;
    }
    public CookieManager getCookieManager() {
        return manager;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void updateUser() { //havent tested yet
        final String URL = "95.85.16.177:3000/api/user/me";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                Log.v("YUPPI on AppSingleton", "Response; " + response.toString());
                try {
                    updatedUser = new User(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("WTF on AppSingleton", "Err: " + error.getLocalizedMessage());
            }
        });

        Volley.newRequestQueue(context.getApplicationContext()).add(jsonObjectRequest);

        if(updatedUser != null) {
            this.user = updatedUser;
        }
        updatedUser = null;
    }
    public void updateFollowingUsers(Context context) {
        final String URL = "http://95.85.16.177:3000/api/user/" + user.getUUID() + "/following";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>(){
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i = 0; i < response.length(); i++) {
                        followingUsers.add(response.getJSONObject(i).getString("_id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("WTF on AppSingleton", "Err: " + error.getLocalizedMessage());
            }
        });
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public ArrayList<String> getFollowingUsers() {
        return followingUsers;
    }
}
