package com.example.acer.rentapp;

import android.content.Intent;
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

import com.example.acer.rentapp.interfaces.GetAssetDataService;
import com.example.acer.rentapp.model.Asset;
import com.example.acer.rentapp.network.RetrofitClientInstance;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAssetActivity extends AppCompatActivity {

    private TextView assetName;
    private TextView assetId;
    private TextView charges;
    private Spinner pickLoc;
    private Spinner dropkLoc;
    private Spinner assetType;
    private Asset asset;
    private Button deleteBut;
    private Button changeBut;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_asset);
        assetName = findViewById(R.id.assetNmae);
        assetId = findViewById(R.id.assetId);
        charges = findViewById(R.id.charges);
        dropkLoc = findViewById(R.id.dropLocation);
        pickLoc = findViewById(R.id.pickLocation);
        assetType = findViewById(R.id.assetType);
        deleteBut = findViewById(R.id.delete);
        changeBut = findViewById(R.id.change);
        pref = PreferenceManager.getDefaultSharedPreferences(EditAssetActivity.this);
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        if(type.compareTo("add")!=0){
            asset = (Asset) intent.getSerializableExtra("Asset");
            assetName.setText(asset.getAssetName());
            assetId.setVisibility(View.GONE);
            deleteBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    
                }
            });
            changeBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    modifyAssets();
                }
            });
        }else{
            deleteBut.setVisibility(View.GONE);
            changeBut.setText("Add");
            changeBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addAssets();
                }
            });
        }

    }

    public void modifyAssets() {
        Log.d("block", "asset");
        GetAssetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetAssetDataService.class);

        Map<String, String> query = new HashMap<>();
        query.put("ASSET_ID", asset.getAssetId());
        query.put("ASSET_NAME", assetName.getText().toString());
        query.put("ASSET_TYPE", assetType.getSelectedItem().toString());
        query.put("LENDER_ID", asset.getUserName());
        query.put("PICKUP_LOCATION", pickLoc.getSelectedItem().toString());
        query.put("DROP_LOCATION", dropkLoc.getSelectedItem().toString());
        query.put("CHARGES", charges.getText().toString());

        Call<Asset> call = service.putAssetCheck(query);
        call.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    Asset data = response.body();

                    if (data == null) {
                        Toast.makeText(EditAssetActivity.this, "FAILED", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(EditAssetActivity.this, "MODIFIED", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
                Log.d("FAILED", t.getMessage());
                Toast.makeText(EditAssetActivity.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addAssets() {
        Log.d("block", "asset");
        GetAssetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetAssetDataService.class);

        Map<String, String> query = new HashMap<>();
        query.put("ASSET_ID", assetId.getText().toString());
        query.put("ASSET_NAME", assetName.getText().toString());
        query.put("ASSET_TYPE", assetType.getSelectedItem().toString());
        query.put("LENDER_ID", pref.getString(getString(R.string.usr_id),""));
        query.put("PICKUP_LOCATION", pickLoc.getSelectedItem().toString());
        query.put("DROP_LOCATION", dropkLoc.getSelectedItem().toString());
        query.put("CHARGES", charges.getText().toString());

        Call<Asset> call = service.postAssetCheck(query);
        call.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    Asset data = response.body();

                    if (data == null) {
                        Toast.makeText(EditAssetActivity.this, "FAILED", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(EditAssetActivity.this, "ADDED", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
                Log.d("FAILED", t.getMessage());
                Toast.makeText(EditAssetActivity.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });
    }
}
