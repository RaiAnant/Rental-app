package com.example.acer.rentapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.rentapp.interfaces.GetAdminDataService;
import com.example.acer.rentapp.interfaces.GetUserDataService;
import com.example.acer.rentapp.model.Admin;
import com.example.acer.rentapp.model.User;
import com.example.acer.rentapp.network.RetrofitClientInstance;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAdminDetails extends AppCompatActivity {

    private Button changeButton;
    private TextView _nameText;
    private TextView _passwordText;
    private TextView _oldPwdText;
    private SharedPreferences sharedPref ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin_details);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        changeButton = findViewById(R.id.change);
        _nameText = findViewById(R.id.input_name);
        _oldPwdText = findViewById(R.id.input_email);
        _passwordText = findViewById(R.id.input_password);

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()) {
                    updateAdmin();
                }
            }
        });
    }

    public void updateAdmin() {
        Log.d("UPDATE", "Login");
        GetAdminDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetAdminDataService.class);

        Map<String, String> query = new HashMap<>();
        query.put("ADMIN_ID", sharedPref.getString(getString(R.string.usr_id),""));
        query.put("ADMIN_NAME", _nameText.getText().toString());
        query.put("USER_PASSWORD", _passwordText.getText().toString());



        Call<Admin> call = service.putAdminCheck(query);
        call.enqueue(new Callback<Admin >() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    Admin data = response.body();
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.usr_name), _nameText.getText().toString());
                    editor.putString(getString(R.string.password), _passwordText.getText().toString());
                    editor.apply();

                    if (data==null) {
                        Toast.makeText(EditAdminDetails.this, "UPDATE FAILED", Toast.LENGTH_LONG).show();

                    } else
                    {
                        Toast.makeText(EditAdminDetails.this, "UPDATED", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Admin> call, Throwable t) {
                Log.d("FAILED", t.getMessage());
                Toast.makeText(EditAdminDetails.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });

    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String password = _passwordText.getText().toString();
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

        String pass = sharedPref.getString(getString(R.string.password), "");
        Log.d("valid", "validate: "+pass);
        if(_oldPwdText.getText().toString().compareTo(pass)!=0){
            _oldPwdText.setError("invalid password");
            valid = false;
        }else{
            _oldPwdText.setError(null);
        }

        return valid;
    }

}
