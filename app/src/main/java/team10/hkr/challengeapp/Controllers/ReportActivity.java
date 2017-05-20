package team10.hkr.challengeapp.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import team10.hkr.challengeapp.Controllers.SignUpActivity.SignUpActivity5ProfilePhoto;
import team10.hkr.challengeapp.GetVideoFrame;
import team10.hkr.challengeapp.Models.Report;
import team10.hkr.challengeapp.R;
import team10.hkr.challengeapp.RequestQueueSingleton;

public class ReportActivity extends Activity {

    private Spinner reportSpinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private TextView reportProfileName;
    private TextView reportHashtag;
    EditText reportDescription;
    String TYPE_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        populateReportForm();

        Button submitReport = (Button) findViewById(R.id.submitReportButton);

        submitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitReport();
            }
        });


    }

    private void submitReport() {

        if (reportSpinner.getSelectedItem().toString().equals("Select Option Here…")) {

            Toast.makeText(this, "Please select a report type.", Toast.LENGTH_SHORT).show();
        }

        else {

            final String URL = "http://95.85.16.177:3000/api/post/" + getIntent().getStringExtra("post_id") + "/report";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(ReportActivity.this, "Report Submitted", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ReportActivity.this, PrimaryActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ReportActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();
                    params.put(TYPE_KEY, getIntent().getStringExtra("username"));
                    params.put("description", reportDescription.getText().toString());
                    return params;
                }
            };

            RequestQueueSingleton.getInstance(this).addToRequestQueue(stringRequest);
        }
    }

    public void populateReportForm(){

        reportSpinner = (Spinner) findViewById(R.id.reportReasons);
        reportProfileName = (TextView) findViewById(R.id.reportProfileName);
        reportHashtag = (TextView) findViewById(R.id.reportHashtag);
        reportDescription = (EditText) findViewById(R.id.reportDescription);

        reportProfileName.setText(getIntent().getStringExtra("username"));
        reportHashtag.setText(getIntent().getStringExtra("tags"));

        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.report_reasons,
                android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reportSpinner.setAdapter(spinnerAdapter);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        reportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TYPE_KEY = spinnerAdapter.toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}

            }
        );


        String CONTENT_URL = getIntent().getStringExtra("contentURL");
        ImageView thumbnail = (ImageView) findViewById(R.id.postBitmap);

        try {
            if (isJpg(CONTENT_URL)) {
                //Images here
                Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(CONTENT_URL).getContent());
                thumbnail.setImageBitmap(bitmap);

            } else if (CONTENT_URL.endsWith(".mp4")) {
                //Videos here

                try {
                    thumbnail.setImageBitmap(GetVideoFrame.retriveVideoFrameFromVideo(CONTENT_URL));
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isJpg(String url) {
        if (url.length() > 4) {
            String tester = url.split("")[url.length()-2] + url.split("")[url.length()-1] + url.split("")[url.length()];
            Log.d("JPGTESTERURL",url);
            Log.d("JPGTESTER", tester);
            if(tester.equals("jpg")) {
                return true;
            }
        }
        return false;
    }


}
