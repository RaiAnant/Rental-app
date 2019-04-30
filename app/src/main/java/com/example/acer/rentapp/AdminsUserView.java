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

public class AdminsUserView extends AppCompatActivity {

    public List<User> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admins_user_view);
        fetchUsers();
    }
    
    public void fetchUsers() {
        Log.d("list", "Login");

        GetUserDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetUserDataService.class);

        Map<String, String> query = new HashMap<>();
        Log.d("where", "outside response");


        Call<List<User>> call = service.getUserCheck(query);
        call.enqueue(new Callback<List<User> >() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    data= response.body();
                } else {
                    Log.d("SUCCESS BUT NO DATA", "NO DATA");
                    Toast.makeText(AdminsUserView.this, "NO RESPONSE", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("FAILED", t.getMessage());
                Toast.makeText(AdminsUserView.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });

    }
}
