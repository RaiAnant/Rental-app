package com.example.acer.rentapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.rentapp.interfaces.GetUserDataService;
import com.example.acer.rentapp.model.User;
import com.example.acer.rentapp.network.RetrofitClientInstance;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    public EditText _nameText;
    public EditText _emailText;
    public EditText _passwordText;
    public Button _signupButton;
    public TextView _loginLink;
    public TextView _phNoText;
    public Spinner _location;
    public User userData;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        _nameText = findViewById(R.id.input_name);
        _emailText = findViewById(R.id.input_email);
        _passwordText = findViewById(R.id.input_password);
        _phNoText = findViewById(R.id.input_number);
        _location = findViewById(R.id.Location);
        _signupButton = findViewById(R.id.btn_signup);
        _loginLink = findViewById(R.id.link_login);


        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

         boolean isSuccess = false;
        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                R.style.Theme_AppCompat_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        GetUserDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetUserDataService.class);

        final String name = _nameText.getText().toString();
        final String email = _emailText.getText().toString();
        final String password = _passwordText.getText().toString();
        final String location = _location.getSelectedItem().toString();
        final String phNo = _phNoText.getText().toString();

        Map<String, String> query = new HashMap<>();
        query.put("USER_ID", email);
        query.put("USER_PASSWORD", password);
        query.put("USER_NAME", name);
        query.put("USER_LOCATION",location);
        query.put("USER_CONTACT",phNo);
        Log.d("where", "outside response");


        Call<User> call = service.postUserCheck(query);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(getString(R.string.usr_id), email);
                    returnIntent.putExtra(getString(R.string.usr_name), name);
                    returnIntent.putExtra(getString(R.string.usr_loc), location);
                    returnIntent.putExtra(getString(R.string.usr_cont), phNo);
                    returnIntent.putExtra(getString(R.string.password), password);
                    progressDialog.dismiss();
                    onSignupSuccess(returnIntent);

                } else {
                    Log.d("SUCCESS BUT NO DATA", "NO DATA");
                    _emailText.setError("User name already exist");
                    progressDialog.dismiss();
                    _signupButton.setEnabled(true);

                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("FAILED", t.getMessage());
                Toast.makeText(RegisterActivity.this, "FAILED", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                _signupButton.setEnabled(true);
            }
        });

    }


    public void onSignupSuccess(Intent returnIntent) {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed, choose another user-name", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String phno = _phNoText.getText().toString();
        if (name.isEmpty() || name.length() < 4) {
            _nameText.setError("at least 4 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || name.length() < 4) {
            _emailText.setError("at least 4 characters");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 ) {
            _passwordText.setError("should be greater than 4 characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if(phno.isEmpty() || !android.util.Patterns.PHONE.matcher(phno).matches()) {
            _phNoText.setError("not a valid phone number");
            valid = false;
        } else {
            _phNoText.setError(null);
        }

        return valid;
    }
}