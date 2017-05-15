package team10.hkr.challengeapp.Controllers.SettingsActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import team10.hkr.challengeapp.R;
import team10.hkr.challengeapp.RequestQueueSingleton;
import team10.hkr.challengeapp.SharedPref;

public class ChangePasswordView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_view);

        Button changePasswordButton = (Button) findViewById(R.id.finalChangePasswordButton);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });

    }

    private void changePassword() {

        final EditText currentPassword = (EditText) findViewById(R.id.currentPasswordEditText);
        final EditText newPasswordEditText = (EditText) findViewById(R.id.newPasswordEditText);
        final EditText verifyPasswordEditText = (EditText) findViewById(R.id.verifyNewPasswordEditText);

        String checkCurrentPassword = SharedPref.read("PASSWORD", "");

        if (!currentPassword.getText().toString().equals(checkCurrentPassword)){

            Toast.makeText(this, "Current password is incorrect.", Toast.LENGTH_SHORT).show();

        }

        else if (!newPasswordEditText.getText().toString().equals(verifyPasswordEditText.getText().toString())){

            Toast.makeText(this, "New passwords do not match.", Toast.LENGTH_SHORT).show();

        }

        else if (currentPassword.getText().toString().equals(checkCurrentPassword) &&
                newPasswordEditText.getText().toString().equals(verifyPasswordEditText.getText().toString())){

            final String CURRENT_PASSWORD = "currentPassword";
            final String NEW_PASSWORD = "newPassword";
            final String VERIFY_PASSWORD = "verifyPassword";
            final String URL = "http://95.85.16.177:3000/api/user/password";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Toast.makeText(ChangePasswordView.this, "Password changed successfully", Toast.LENGTH_LONG).show();
                    SharedPref.write("PASSWORD", newPasswordEditText.getText().toString());

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ChangePasswordView.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put(CURRENT_PASSWORD, currentPassword.getText().toString());
                    params.put(NEW_PASSWORD, newPasswordEditText.getText().toString());
                    params.put(VERIFY_PASSWORD, verifyPasswordEditText.getText().toString());
                    return params;
                }
            };

            RequestQueueSingleton.getInstance(this).addToRequestQueue(stringRequest);
        }

        }

}

