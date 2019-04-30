package com.example.acer.rentapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.acer.rentapp.interfaces.GetRequestDataService;
import com.example.acer.rentapp.model.Request;
import com.example.acer.rentapp.network.RetrofitClientInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SentRequests extends AppCompatActivity {

    public List<Request> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_requests);
        getSentRequests();
    }

    public void getSentRequests() {

        Log.d("Request", "get");

        GetRequestDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetRequestDataService.class);

        Map<String, String> query = new HashMap<>();
        query.put("USER_ID", "");
        query.put("USER", "CUSTOMER");



        Call<List<Request>> call = service.getRequestCheck(query);
        call.enqueue(new Callback<List<Request> >() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    data= response.body();

                    if(data.size()==0){
                        Toast.makeText(SentRequests.this, "NO REQUESTS", Toast.LENGTH_LONG).show();
                        }
                }else {
                    Log.d("SUCCESS BUT NO DATA", "NO DATA");
                    Toast.makeText(SentRequests.this, "NO RESPONSE", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {
                Log.d("FAILED", t.getMessage());
                Toast.makeText(SentRequests.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });

    }
}
