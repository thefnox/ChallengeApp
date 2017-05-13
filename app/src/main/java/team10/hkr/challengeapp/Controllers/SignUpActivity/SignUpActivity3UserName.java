package team10.hkr.challengeapp.Controllers.SignUpActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import team10.hkr.challengeapp.Controllers.LoginActivity;
import team10.hkr.challengeapp.Controllers.PrimaryActivity;
import team10.hkr.challengeapp.Models.User;
import team10.hkr.challengeapp.R;
import team10.hkr.challengeapp.RequestQueueSingleton;

public class SignUpActivity3UserName extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_3_user_name);

    }

    public void onContinueClick(View view) {
        //changing the activity with the button

        EditText username = (EditText) findViewById(R.id.CreateUsernamePlainText);

        final String email = getIntent().getStringExtra("email");
        final String firstName = getIntent().getStringExtra("firstName");
        final String lastName = getIntent().getStringExtra("lastName");
        final String password = getIntent().getStringExtra("password");
        final String usernameString = username.getText().toString();

        final String SIGNUP_URL = "http://95.85.16.177:3000/api/auth/signup";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, SIGNUP_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(SignUpActivity3UserName.this, response, Toast.LENGTH_LONG).show();
                Intent mIntent = new Intent(SignUpActivity3UserName.this, SignUpActivity5ProfilePhoto.class);
                mIntent.putExtra("username", usernameString);
                mIntent.putExtra("password", password);
                startActivity(mIntent);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignUpActivity3UserName.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("firstName", firstName);
                params.put("lastName", lastName);
                params.put("username", usernameString);
                params.put("password", password);
                return params;
            }
        };

        RequestQueueSingleton.getInstance(this).addToRequestQueue(stringRequest);


    }


}
