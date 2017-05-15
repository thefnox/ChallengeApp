package team10.hkr.challengeapp.Controllers.SettingsActivity;

import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import team10.hkr.challengeapp.Controllers.LoginActivity;
import team10.hkr.challengeapp.Controllers.PrimaryActivity;
import team10.hkr.challengeapp.Models.User;
import team10.hkr.challengeapp.R;
import team10.hkr.challengeapp.RequestQueueSingleton;
import team10.hkr.challengeapp.SharedPref;

public class ChangeNameView extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name_view);
        TextView currentName = (TextView) findViewById(R.id.currentNameTextView);
        Button changeNameButton = (Button) findViewById(R.id.changeNameButton);
        currentName.setText(SharedPref.read("FIRST_NAME", "") + " " + SharedPref.read("LAST_NAME", ""));


        changeNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeName();
            }
        });
    }

    private void changeName() {

        final EditText newFirstName = (EditText) findViewById(R.id.newFirstName);
        final EditText newLastName = (EditText) findViewById(R.id.newLastName);
        final String FIRST_NAME = "firstName";
        final String LAST_NAME = "lastName";
        final String URL = "http://95.85.16.177:3000/api/user";
        final TextView currentName = (TextView) findViewById(R.id.currentNameTextView);


        if (newFirstName.getText().toString().equals("") && newLastName.getText().toString().equals("")) {
            Toast.makeText(this, "No Name Entered", Toast.LENGTH_SHORT).show();

        } else if (newFirstName.getText().equals("")) {
            Toast.makeText(this, "No First Name", Toast.LENGTH_SHORT).show();

        } else if (newLastName.getText().equals("")) {
            Toast.makeText(this, "No Last Name", Toast.LENGTH_SHORT).show();

        } else if (!newFirstName.getText().toString().equals("") && !newLastName.getText().toString().equals("")) {

            StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Toast.makeText(ChangeNameView.this, "Name changed successfully", Toast.LENGTH_LONG).show();
                    SharedPref.write("FIRST_NAME", newFirstName.getText().toString());
                    SharedPref.write("LAST_NAME", newLastName.getText().toString());
                    currentName.setText(SharedPref.read("FIRST_NAME", "") + " " + SharedPref.read("LAST_NAME", ""));

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ChangeNameView.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put(FIRST_NAME, newFirstName.getText().toString());
                    params.put(LAST_NAME, newLastName.getText().toString());
                    return params;
                }
            };

            RequestQueueSingleton.getInstance(this).addToRequestQueue(stringRequest);

        }
    }
}
