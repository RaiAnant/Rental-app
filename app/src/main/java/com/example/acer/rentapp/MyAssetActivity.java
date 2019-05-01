package com.example.acer.rentapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.acer.rentapp.adapters.MyAdapter;
import com.example.acer.rentapp.adapters.RentAdapter;
import com.example.acer.rentapp.interfaces.GetAssetDataService;
import com.example.acer.rentapp.interfaces.GetUserDataService;
import com.example.acer.rentapp.model.Asset;
import com.example.acer.rentapp.model.User;
import com.example.acer.rentapp.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class MyAssetActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Asset> assetData;
    private String type;
    private MyAdapter adapter;
    private Context context;
    private SharedPreferences pref;
    private FloatingActionButton addButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_asset);
        addButton = findViewById(R.id.floatingActionButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyAssetActivity.this,EditAssetActivity.class);
                intent.putExtra("type","add");
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        assetData = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyAssetActivity.this));
        type = "any";
        context = MyAssetActivity.this;
        pref = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void getAssetList() {
        Log.d(TAG, "Asset");


        GetAssetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetAssetDataService.class);

        Map<String, String> query = new HashMap<>();
        Log.d("where", "outside response");
        query.put("LENDER_ID", pref.getString(getString(R.string.usr_id),""));

        Call<List<Asset>> call = service.getAssetCheck(query);
        call.enqueue(new Callback<List<Asset>>() {
            @Override
            public void onResponse(Call<List<Asset>> call, Response<List<Asset>> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    List<Asset> data = response.body();
                    assetData = (ArrayList<Asset>) data;
                    adapter = new MyAdapter(assetData,context);
                    recyclerView.setAdapter(adapter);
                } else {
                    Log.d("SUCCESS BUT NO DATA", "NO DATA");

                    Toast.makeText(MyAssetActivity.this, "FAILED", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Asset>> call, Throwable t) {
                Log.d("FAILED", t.getMessage());

            }
        });

    }




    @Override
    protected void onStart() {
        super.onStart();
        getAssetList();
    }
}
