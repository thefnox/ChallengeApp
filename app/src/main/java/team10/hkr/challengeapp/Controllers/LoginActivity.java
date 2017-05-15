package team10.hkr.challengeapp.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.CookieHandler;
import java.util.HashMap;
import java.util.Map;
import team10.hkr.challengeapp.AppSingleton;
import team10.hkr.challengeapp.Models.User;
import team10.hkr.challengeapp.R;
import team10.hkr.challengeapp.Controllers.SignUpActivity.SignUpActivity1Email;
import team10.hkr.challengeapp.RequestQueueSingleton;
import team10.hkr.challengeapp.SharedPref;

public class LoginActivity extends AppCompatActivity {

    LoginButton facebook_login_button;
    CallbackManager callbackManager;
    AppSingleton sessionManager = AppSingleton.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        initializeControls();
        loginWithFB();
        CookieHandler.setDefault(sessionManager.getCookieManager());
        Button mButton = (Button) findViewById(R.id.sign_in_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logInUser();
            }
        });

        SharedPref.init(getApplicationContext());

        if (!SharedPref.read("USERNAME_OR_EMAIL", "").equals("")) {

            EditText usernameEditText = (EditText) findViewById(R.id.email_user_edittext_signin);
            EditText passwordEditText = (EditText) findViewById(R.id.password_edittext_signin);
            CheckBox rememberMeBox = (CheckBox) findViewById(R.id.rememberMeBox);

            usernameEditText.setText(SharedPref.read("USERNAME_OR_EMAIL", ""));
            passwordEditText.setText(SharedPref.read("PASSWORD", ""));
            rememberMeBox.setChecked(true);
        }

    }

    public void createAccount(View view) {
        //changing the activity with the button
        Intent myIntent = new Intent(LoginActivity.this, SignUpActivity1Email.class);
        startActivity(myIntent);
    }

    private void initializeControls() {
        callbackManager = CallbackManager.Factory.create();
        facebook_login_button = (LoginButton)findViewById(R.id.facebook_login_button);
    }

    private void logInUser() {
        final String USERNAME_OR_EMAIL_KEY = "usernameOrEmail";
        final String PASSWORD_KEY = "password";
        final String SIGNIN_URL = "http://95.85.16.177:3000/api/auth/signin";
        final EditText usernameEditText = (EditText) findViewById(R.id.email_user_edittext_signin);
        final EditText passwordEditText = (EditText) findViewById(R.id.password_edittext_signin);
        final CheckBox rememberMe = (CheckBox) findViewById(R.id.rememberMeBox);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, SIGNIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(LoginActivity.this,"Logged in as " + usernameEditText.getText(), Toast.LENGTH_LONG).show();
                Intent mIntent = new Intent(LoginActivity.this, PrimaryActivity.class);

                if (rememberMe.isChecked()){

                    SharedPref.write("USERNAME_OR_EMAIL", usernameEditText.getText().toString());
                    SharedPref.write("PASSWORD", passwordEditText.getText().toString());

                } else {

                    SharedPref.write("USERNAME_OR_EMAIL", "");
                    SharedPref.write("PASSWORD", "");

                }

                try {
                    JSONObject json = new JSONObject(response);
                    User user = new User(json);
                    SharedPref.write("UUID", user.getUUID());
                    SharedPref.write("EMAIL", user.getEmail());
                    SharedPref.write("FIRST_NAME", user.getFirstName());
                    SharedPref.write("LAST_NAME", user.getLastName());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mIntent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put(USERNAME_OR_EMAIL_KEY, usernameEditText.getText().toString());
                params.put(PASSWORD_KEY, passwordEditText.getText().toString());
                return params;
            }
        };

        RequestQueueSingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void loginWithFB() {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
