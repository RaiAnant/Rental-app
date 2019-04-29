package com.example.acer.rentapp;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.acer.rentapp.model.Asset;
import com.example.acer.rentapp.model.User;
import com.example.acer.rentapp.network.RetrofitClientInstance;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class AssetPickup extends AppCompatActivity {

    public Asset assetData;
    public Asset asset;
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
    public Button button;

    public User  lender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_pickup);
        Intent intent = getIntent();
        asset = (Asset) intent.getSerializableExtra("Asset");
        assetImg = findViewById(R.id.imageView2);
        assetName = findViewById(R.id.assetNmae);
        assetId = findViewById(R.id.assetId);
        assetCost = findViewById(R.id.assetCost);
        assetDrop = findViewById(R.id.assetDrop);
        assetPickup = findViewById(R.id.assetPick);
        lenderName = findViewById(R.id.lenderName);
        lenderId  = findViewById(R.id.lenderId);
        lenderLoc = findViewById(R.id.lenderLoc);
        lenderPhno = findViewById(R.id.lenderContact);

        assetName.setText(asset.getAssetName());
        assetId.setText(asset.getAssetId());
        assetPickup.setText(asset.getPickupLocation());
        assetDrop.setText(asset.getDropLocation());
        assetCost.setText(asset.getCharges());
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void rentAsset() {
        Log.d(TAG, "rent");

        GetAssetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetAssetDataService.class);

        Date date=new Date(20,02,22);
        Timestamp timestamp = new Timestamp(date.getTime());
        Timestamp myTimeStamp= timestamp;

        Map<String, String> query = new HashMap<>();
        query.put("CUSTOMER_ID", "MACH");
        query.put("ASSET_ID", "asset004");
        query.put("PICKUP_TIME", timestamp.toString());
        query.put("DROP_TIME", timestamp.toString());
        query.put("ASSET_PICKUP", "YES");
        Log.d("where", "outside response");


        Call<Asset> call = service.putAssetCheck(query);
        call.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    Asset data = response.body();
                    assetData = data;
                    Log.d("Response123", assetData.toString());

//
//                    for (User user : response.body()) {
//                        Log.wtf("Response", "" + user.getUserName());
//                        Toast.makeText(LoginActivity.this, user.getUserName(), Toast.LENGTH_LONG).show();
//
//                    }
//                    Log.d("data--",data.toString());
//                    Log.d("SUCCESS", response.raw().toString());

                } else {
                    Log.d("SUCCESS BUT NO DATA", "NO DATA");
                    Toast.makeText(AssetPickup.this , "FAILED", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
                Log.d("FAILED", t.getMessage());

            }
        });

    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        }
    }
    public void lenderInfo() {
        Log.d(TAG, "Login");

        final ProgressDialog progressDialog = new ProgressDialog(AssetPickup.this,
                R.style.Theme_AppCompat_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();



        GetUserDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetUserDataService.class);

        Map<String, String> query = new HashMap<>();
        query.put("USER_ID", "put lender_id here");
        Log.d("where", "outside response");


        Call<List<User>> call = service.getUserCheck(query);
        call.enqueue(new Callback<List<User> >() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    List<User> data= response.body();



                }else {
                    Log.d("SUCCESS BUT NO DATA", "NO DATA");
                    Toast.makeText(AssetPickup.this, "NO RESPONSE", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("FAILED", t.getMessage());
                Toast.makeText(AssetPickup.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });

    }

}
