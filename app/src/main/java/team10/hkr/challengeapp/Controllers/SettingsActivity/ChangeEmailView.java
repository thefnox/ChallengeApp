package team10.hkr.challengeapp.Controllers.SettingsActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import team10.hkr.challengeapp.AppSingleton;
import team10.hkr.challengeapp.R;
import team10.hkr.challengeapp.RequestQueueSingleton;
import team10.hkr.challengeapp.SharedPref;

public class ChangeEmailView extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_email_view);
        Button changeEmailButton = (Button) findViewById(R.id.changeEmailButton);
        TextView currentEmail = (TextView) findViewById(R.id.currentEmailTextView);
        currentEmail.setText(AppSingleton.getInstance().getUser().getEmail());


        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeEmail();
            }
        });
    }

    private void changeEmail() {

        final EditText newEmail = (EditText) findViewById(R.id.newEmailEditText);
        final String EMAIL = "email";
        final String URL = "http://95.85.16.177:3000/api/user";
        final TextView currentEmail = (TextView) findViewById(R.id.currentEmailTextView);


        if (newEmail.getText().toString().equals("")) {
            Toast.makeText(this, "No Email Entered", Toast.LENGTH_SHORT).show();

        } else {

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Toast.makeText(ChangeEmailView.this, "Email changed successfully", Toast.LENGTH_LONG).show();
                    SharedPref.write("EMAIL", newEmail.getText().toString());
                    AppSingleton.getInstance().getUser().setEmail(newEmail.getText().toString());
                    currentEmail.setText(SharedPref.read("EMAIL", ""));
                    newEmail.setText("");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ChangeEmailView.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put(EMAIL, newEmail.getText().toString())
                    ;
                    return params;
                }
            };

            RequestQueueSingleton.getInstance(this).addToRequestQueue(stringRequest);
        }
    }
}

