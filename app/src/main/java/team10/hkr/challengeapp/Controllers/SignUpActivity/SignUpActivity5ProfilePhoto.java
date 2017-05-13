package team10.hkr.challengeapp.Controllers.SignUpActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.net.CookieHandler;
import java.util.HashMap;
import java.util.Map;

import team10.hkr.challengeapp.Controllers.AppSingleton;
import team10.hkr.challengeapp.Controllers.PrimaryActivity;
import team10.hkr.challengeapp.R;

import team10.hkr.challengeapp.R;
import team10.hkr.challengeapp.RequestQueueSingleton;

public class SignUpActivity5ProfilePhoto extends AppCompatActivity {

    AppSingleton sessionManager = AppSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_5_profile_photo);
        CookieHandler.setDefault(sessionManager.getCookieManager());
    }


    public void onFinishClick(View view) {

        final String USERNAME_OR_EMAIL_KEY = "usernameOrEmail";
        final String PASSWORD_KEY = "password";
        final String SIGNIN_URL = "http://95.85.16.177:3000/api/auth/signin";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SIGNIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SignUpActivity5ProfilePhoto.this, "Signed in as " +
                        getIntent().getStringExtra("username"), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(SignUpActivity5ProfilePhoto.this, PrimaryActivity.class);
                startActivity(i);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUpActivity5ProfilePhoto.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put(USERNAME_OR_EMAIL_KEY, getIntent().getStringExtra("username"));
                params.put(PASSWORD_KEY, getIntent().getStringExtra("password"));
                return params;
            }
        };

        RequestQueueSingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}

