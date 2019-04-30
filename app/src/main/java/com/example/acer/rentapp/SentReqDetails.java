package com.example.acer.rentapp;

import android.content.Intent;
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

import com.example.acer.rentapp.interfaces.GetUserDataService;
import com.example.acer.rentapp.model.Asset;
import com.example.acer.rentapp.model.Request;
import com.example.acer.rentapp.model.User;
import com.example.acer.rentapp.network.RetrofitClientInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SentReqDetails extends AppCompatActivity {

    public Request requestData;
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
    public Button buttonUser;
    public boolean isLenderChildAdded;
    private User lender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_req_details);

        Intent intent = getIntent();

        requestData = (Request) intent.getSerializableExtra("Asset");

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
        isLenderChildAdded = false;

        buttonUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isLenderChildAdded) {
                    lenderInfo();
                    isLenderChildAdded = true;
                    buttonUser.setEnabled(false);
                }
            }
        });

    }

    public void lenderInfo() {
        Log.d(TAG, "Login");

//        final ProgressDialog progressDialog = new ProgressDialog(AssetPickup.this,
//                R.style.Theme_AppCompat_Dialog);
//        progressDialog.setIndeterminate(true);
//        progressDialog.setMessage("Authenticating...");
//        progressDialog.show();



        GetUserDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetUserDataService.class);

        Map<String, String> query = new HashMap<>();
        query.put("USER_ID", asset.getUserName());
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
                    Toast.makeText(SentReqDetails.this, "NO RESPONSE", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("FAILED", t.getMessage());
                Toast.makeText(SentReqDetails.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void inflateUserCard(){
        LinearLayout linearLayout = findViewById(R.id.asset_detail_linear);
        View v = LayoutInflater.from(SentReqDetails.this).inflate(R.layout.user_card, null);
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
