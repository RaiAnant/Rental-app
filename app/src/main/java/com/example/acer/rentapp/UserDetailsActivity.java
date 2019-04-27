package com.example.acer.rentapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
    }

    public void updateUser() {
        Log.d("UPDATE", "Login");
        GetUserDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetUserDataService.class);

        Map<String, String> query = new HashMap<>();
        query.put("USER_ID", "");
        query.put("USER_NAME", "");
        query.put("USER_LOCATION", "");
        query.put("USER_CONTACT", "");
        query.put("USER_PASSWORD", "");



        Call<User> call = service.putUserCheck(query);
        call.enqueue(new Callback<User >() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    User data = response.body();

                    if (data!=null) {
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
}
