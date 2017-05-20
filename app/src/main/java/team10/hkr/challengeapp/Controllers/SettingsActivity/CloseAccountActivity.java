package team10.hkr.challengeapp.Controllers.SettingsActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import team10.hkr.challengeapp.Controllers.LoginActivity;
import team10.hkr.challengeapp.R;
import team10.hkr.challengeapp.RequestQueueSingleton;

public class CloseAccountActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_account);

        Button closeAccBtn = (Button) findViewById(R.id.closeAccountButton);

        closeAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(CloseAccountActivity.this)

                        .setTitle("Close Account")
                        .setMessage("Are you sure you want to close your account?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                closeAccount();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(CloseAccountActivity.this, "We are so glad you changed your mind!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }

    private void closeAccount() {

        final String URL = "http://95.85.16.177:3000/api/user/";

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(CloseAccountActivity.this,"We are sorry to see you go...", Toast.LENGTH_LONG).show();
                Intent mIntent = new Intent(CloseAccountActivity.this, LoginActivity.class);

                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mIntent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CloseAccountActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueueSingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

}

