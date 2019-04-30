package com.example.acer.rentapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.acer.rentapp.adapters.RequestAdapter;
import com.example.acer.rentapp.interfaces.GetRequestDataService;
import com.example.acer.rentapp.model.Request;
import com.example.acer.rentapp.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeRequestActivity extends AppCompatActivity {

    private ArrayList<Request> assetData;
    private Context context;
    private RequestAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_request);
        recyclerView = findViewById(R.id.recyclerView);
        assetData = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(SeeRequestActivity.this));
        context = SeeRequestActivity.this;
        
    }

    public void getRecievedRequests() {

        Log.d("Request", "get");

        GetRequestDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetRequestDataService.class);

        Map<String, String> query = new HashMap<>();
        query.put("USER_ID", "");
        query.put("USER", "LENDER");



        Call<List<Request>> call = service.getRequestCheck(query);
        call.enqueue(new Callback<List<Request> >() {
            @Override
            public void onResponse(Call<List<Request>> call, Response<List<Request>> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    List<Request> data= response.body();

                    assetData = (ArrayList<Request>) data;

//                    Log.d("list12",String.valueOf(assetData.size()));
                    adapter = new RequestAdapter(assetData,context);
                    recyclerView.setAdapter(adapter);
                    if(data.size()==0){
                        Toast.makeText(SeeRequestActivity.this, "NO REQUESTS", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Log.d("SUCCESS BUT NO DATA", "NO DATA");
                    Toast.makeText(SeeRequestActivity.this, "NO RESPONSE", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Request>> call, Throwable t) {
                Log.d("FAILED", t.getMessage());
                Toast.makeText(SeeRequestActivity.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        getRecievedRequests();
    }
}
