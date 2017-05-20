package team10.hkr.challengeapp.Controllers;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import team10.hkr.challengeapp.R;

public class ReportActivity extends Activity {

    private Spinner reportSpinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;
    private TextView reportProfileName;
    private TextView reportHashtag;
    EditText reportDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        populateReportForm();

    }

    public void populateReportForm(){

        reportSpinner = (Spinner) findViewById(R.id.reportReasons);
        reportProfileName = (TextView) findViewById(R.id.reportProfileName);
        reportHashtag = (TextView) findViewById(R.id.reportHashtag);
        reportDescription = (EditText) findViewById(R.id.reportDescription);

        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.report_reasons,
                android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reportSpinner.setAdapter(spinnerAdapter);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        reportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){



            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getBaseContext(), parent.getItemAtPosition(position)+
                        " selected", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}

            }
        );




    }


}
