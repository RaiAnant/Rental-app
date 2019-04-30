package com.example.acer.rentapp;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.rentapp.model.User;
import com.example.acer.rentapp.network.RetrofitClientInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsActivity extends AppCompatActivity {

    private Button changeButton;
    private TextView _nameText;
    private TextView _passwordText;
    private TextView _phNoText;
    private TextView _oldPwdText;
    private SharedPreferences sharedPref ;
    private Spinner spinner;
    private TextView oldPwdText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        changeButton = findViewById(R.id.change);
        _nameText = findViewById(R.id.input_name);
        _oldPwdText = findViewById(R.id.input_email);
        _passwordText = findViewById(R.id.input_password);
        _phNoText = findViewById(R.id.input_number);
        spinner = findViewById(R.id.Location);

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()) {
                    updateUser();
                }
            }
        });

    }

    public void updateUser() {
        Log.d("UPDATE", "Login");
        GetUserDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetUserDataService.class);

        Map<String, String> query = new HashMap<>();
        query.put("USER_ID", sharedPref.getString(getString(R.string.usr_id),""));
        query.put("USER_NAME", _nameText.getText().toString());
        query.put("USER_LOCATION", spinner.getSelectedItem().toString());
        query.put("USER_CONTACT", _phNoText.getText().toString());
        query.put("USER_PASSWORD", _passwordText.getText().toString());



        Call<User> call = service.putUserCheck(query);
        call.enqueue(new Callback<User >() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    User data = response.body();
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.usr_name), _nameText.getText().toString());
                    editor.putString(getString(R.string.usr_loc), spinner.getSelectedItem().toString());
                    editor.putString(getString(R.string.usr_cont), _phNoText.getText().toString());
                    editor.putString(getString(R.string.password), _passwordText.getText().toString());
                    editor.apply();

                    if (data==null) {
                        Toast.makeText(UserDetailsActivity.this, "UPDATE FAILED", Toast.LENGTH_LONG).show();

                    } else
                     {
                        Toast.makeText(UserDetailsActivity.this, "UPDATED", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("FAILED", t.getMessage());
                Toast.makeText(UserDetailsActivity.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });

    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String password = _passwordText.getText().toString();
        String phno = _phNoText.getText().toString();
        if (name.isEmpty() || name.length() < 4) {
            _nameText.setError("at least 4 characters");
            valid = false;
        } else {
            _nameText.setError(null);
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

        String pass = sharedPref.getString(getString(R.string.password), "");

        if(_oldPwdText.getText().toString().compareTo(pass)!=0){
            _oldPwdText.setError("invalid password");
            valid = false;
        }else{
            _oldPwdText.setError(null);
        }

        return valid;
    }
}
