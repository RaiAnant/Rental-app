package com.example.acer.rentapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.rentapp.model.User;
import com.example.acer.rentapp.network.RetrofitClientInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
//    private static final Object RetrofitClientInstance = ;

    public EditText _emailText;
    public EditText _passwordText;
    public Button _loginButton;
    public TextView _signupLink;
    public static User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        chckLogin();

        _emailText = findViewById(R.id.input_email);
        _passwordText = findViewById(R.id.input_password);
        _loginButton = findViewById(R.id.btn_login);
        _signupLink = findViewById(R.id.link_signup);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.Theme_AppCompat_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        GetUserDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetUserDataService.class);

        Map<String, String> query = new HashMap<>();
        query.put("USER_ID", email);
        query.put("USER_PASSWORD", password);
        Log.d("where", "outside response");


        Call<List<User>> call = service.getUserCheck(query);
        call.enqueue(new Callback<List<User> >() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    List<User> data= response.body();

                    if(data.size()==0){
                        progressDialog.dismiss();
                        onLoginFailed();
                        return;
                    }else{
                        user = data.get(0);
                        saveUserData(user);
                        progressDialog.dismiss();
                        onLoginSuccess();
                    }

                } else {
                    Log.d("SUCCESS BUT NO DATA", "NO DATA");
                    Toast.makeText(LoginActivity.this, "NO RESPONSE", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("FAILED", t.getMessage());
                Toast.makeText(LoginActivity.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                user = new User(data.getStringExtra(getString(R.string.usr_id)),data.getStringExtra(getString(R.string.usr_name)),data.getStringExtra(getString(R.string.usr_loc)),data.getStringExtra(getString(R.string.usr_cont)),data.getStringExtra(getString(R.string.password)));
                saveUserData(user);
                startHomeActivity();
                this.finish();
            }
        }
    }


    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        startHomeActivity();
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || email.length()<4) {
            _emailText.setError("userName should be at least 5 characters");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public void startHomeActivity(){
        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(intent);
    }

    public void chckLogin(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String usr_name = sharedPref.getString(getString(R.string.usr_id), "");
        if(usr_name.compareTo("")!=0){
            Toast.makeText(LoginActivity.this, usr_name, Toast.LENGTH_LONG).show();
            user  = new User(sharedPref.getString(getString(R.string.usr_id), ""),sharedPref.getString(getString(R.string.usr_name), ""),sharedPref.getString(getString(R.string.usr_loc), ""),sharedPref.getString(getString(R.string.usr_cont), ""),sharedPref.getString(getString(R.string.password), ""));
            startHomeActivity();
            finish();
        }
    }

    public void saveUserData(User user){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.usr_id), user.getUserName());
        editor.putString(getString(R.string.usr_name), user.getName());
        editor.putString(getString(R.string.usr_loc), user.getLocation());
        editor.putString(getString(R.string.usr_cont), user.getPhno());
        editor.putString(getString(R.string.password), user.getPassword());
        editor.apply();
    }
}