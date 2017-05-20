package team10.hkr.challengeapp.Controllers.SignUpActivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import team10.hkr.challengeapp.R;

public class SignUpActivity2FullNamePassword extends Activity {

    private static final Pattern VALID_PASSWORD =
            Pattern.compile("^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{10,}$");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_2__full_name_password);
    }

    public void onContinueClick(View view) {
        //changing the activity with the button

        EditText firstName = (EditText) findViewById(R.id.firstNameEditText);
        EditText lastName = (EditText) findViewById(R.id.lastNameEditText);
        EditText password = (EditText) findViewById(R.id.createPasswordEnterText);
        EditText confirmPassword = (EditText) findViewById(R.id.confirmPasswordTextEdit);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        Matcher matcher = VALID_PASSWORD.matcher(password.getText().toString());


        if (!matcher.matches()){

            Toast.makeText(this, "Password Requirements: \n Minimum 10 Characters \n 1 Upper Case, 1 Number, 1 Special Character",
                    Toast.LENGTH_SHORT).show();

        }

        else if(!password.getText().toString().equals(confirmPassword.getText().toString())){

            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
        }

        else if (matcher.matches() && password.getText().toString().equals(confirmPassword.getText().toString())){

            Intent myIntent = new Intent(SignUpActivity2FullNamePassword.this, SignUpActivity3UserName.class);
            myIntent.putExtra("email", email);
            myIntent.putExtra("firstName", firstName.getText().toString());
            myIntent.putExtra("lastName", lastName.getText().toString());
            myIntent.putExtra("password", password.getText().toString());
            startActivity(myIntent);

        }

    }

}
