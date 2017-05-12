package team10.hkr.challengeapp.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import team10.hkr.challengeapp.Models.Pokemon;
import team10.hkr.challengeapp.R;
import team10.hkr.challengeapp.Controllers.SignUpActivity.SignUpActivity1Email;

public class LoginActivity extends AppCompatActivity {

    LoginButton facebook_login_button;
    CallbackManager callbackManager;
    EditText usernameOrEmail;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        initializeControls();
        loginWithFB();
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
