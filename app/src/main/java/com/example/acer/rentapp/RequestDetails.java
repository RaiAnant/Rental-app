package com.example.acer.rentapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.acer.rentapp.adapters.RentAdapter;
import com.example.acer.rentapp.interfaces.GetAssetDataService;
import com.example.acer.rentapp.interfaces.GetRequestDataService;
import com.example.acer.rentapp.interfaces.GetUserDataService;
import com.example.acer.rentapp.model.Asset;
import com.example.acer.rentapp.model.Request;
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

public class RequestDetails extends AppCompatActivity {

    private Asset asset;
    private Request request;
    public ImageView assetImg;
    public TextView assetName;
    public TextView assetId;
    public TextView assetCost;
    public TextView assetDrop;
    public TextView assetPickup;
    public TextView lenderName;
    public TextView lenderId;
    public TextView lenderPhno;
    public TextView lenderLoc;
    public Button buttonUser,rentButton;
    public boolean isLenderChildAdded;
    private User lender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_details);

        Intent intent = getIntent();
        request = (Request) intent.getSerializableExtra("Asset");
        assetImg = findViewById(R.id.imageView2);
        assetName = findViewById(R.id.assetNmae);
        assetId = findViewById(R.id.assetId);
        assetCost = findViewById(R.id.assetCost);
        assetDrop = findViewById(R.id.assetDrop);
        assetPickup = findViewById(R.id.assetPick);
        buttonUser = findViewById(R.id.userButton);
        rentButton = findViewById(R.id.rentButton);

        buttonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isLenderChildAdded) {
                    customreInfo();
                    isLenderChildAdded = true;
                    buttonUser.setEnabled(false);
                }
            }
        });

        rentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lendToCustomer();
            }
        });

        getAsset();
    }

    public void customreInfo() {
        Log.d(TAG, "Login");

//        final ProgressDialog progressDialog = new ProgressDialog(AssetPickup.this,
//                R.style.Theme_AppCompat_Dialog);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Authenticating...");
//        progressDialog.show();



        GetUserDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetUserDataService.class);

        Map<String, String> query = new HashMap<>();
        Log.d("custid", request.getCustomerId());
        query.put("USER_ID", request.getCustomerId());
        Log.d("where", "outside response");



        Call<List<User>> call = service.getUserCheck(query);
        call.enqueue(new Callback<List<User> >() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    List<User> data= response.body();
                    lender = data.get(0);
                    inflateUserCard();

                }else {
                    Log.d("SUCCESS BUT NO DATA", "NO DATA");
                    Toast.makeText(RequestDetails.this, "NO RESPONSE", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("FAILED", t.getMessage());
                Toast.makeText(RequestDetails.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });

    }


    public void lendToCustomer() {
        Log.d("Lend", "Customer");
        GetRequestDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetRequestDataService.class);
        Map<String, String> query = new HashMap<>();
        query.put("ASSET_ID", request.getAssetId());
        query.put("CUSTOMER_ID", request.getCustomerId());
        query.put("PICKUP_TIME", request.getPickupTime());
        query.put("DROP_TIME", request.getDropTime());
        //Log.d("time", timeSplitStart[2]+"-"+timeSplitStart[1]+"-"+timeSplitStart[0]+" "+startTime[1].substring(0,startTime[1].indexOf("Z")) +"----"+timeSplitEnd[2]+"-"+timeSplitEnd[1]+"-"+timeSplitEnd[0]+" "+endTime[1].substring(0,endTime[1].indexOf("Z")));


        Call<Request> call = service.putRequestCheck(query);
        call.enqueue(new Callback<Request >() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                Log.d("where", "inside response");

                Toast.makeText(RequestDetails.this, "RENTED", Toast.LENGTH_LONG).show();

            }
            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                Log.d("FAILED", t.getMessage());
                Toast.makeText(RequestDetails.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });

    }


    public void getAsset() {
        Log.d(TAG, "Login");


        GetAssetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetAssetDataService.class);

        Map<String, String> query = new HashMap<>();
        query.put("IS_AVAIL", "YES");
        query.put(("ASSET_ID"),request.getAssetId());
        Log.d("where", "outside response");


        Call<List<Asset>> call = service.getAssetCheck(query);
        call.enqueue(new Callback<List<Asset>>() {
            @Override
            public void onResponse(Call<List<Asset>> call, Response<List<Asset>> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    List<Asset> data = response.body();
                    asset = (Asset) data.get(0);
                    assetName.setText(asset.getAssetName());
                    assetId.setText(asset.getAssetId());
                    if(asset.getAssetType().compareTo("car")==0){
                        assetImg.setImageResource(R.drawable.car);
                    }else{
                        assetImg.setImageResource(R.drawable.bike);
                    }
                    assetPickup.setText(request.getPickupTime());
                    assetDrop.setText(request.getDropTime());
                    assetCost.setText(request.getRent());

                } else {
                    Log.d("SUCCESS BUT NO DATA", "NO DATA");
                    Toast.makeText(RequestDetails.this, "FAILED", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Asset>> call, Throwable t) {
                Log.d("FAILED", t.getMessage());

            }
        });

    }

    public void inflateUserCard(){
        LinearLayout linearLayout = findViewById(R.id.asset_detail_linear);
        View v = LayoutInflater.from(RequestDetails.this).inflate(R.layout.user_card, null);
        linearLayout.addView(v);
        TextView name = v.findViewById(R.id.lenderName);
        TextView id = v.findViewById(R.id.lenderId);
        TextView contact = v.findViewById(R.id.lenderContact);
        TextView loc = v.findViewById(R.id.lenderLoc);
        name.setText(lender.getName());
        id.setText(lender.getUserName());
        contact.setText(lender.getPhno());
        loc.setText(lender.getLocation());
    }
}
