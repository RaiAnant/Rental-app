package com.example.acer.rentapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.acer.rentapp.interfaces.GetAssetDataService;
import com.example.acer.rentapp.interfaces.GetRequestDataService;
import com.example.acer.rentapp.interfaces.GetUserDataService;
import com.example.acer.rentapp.model.Asset;
import com.example.acer.rentapp.model.Request;
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

public class AssetPickup extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

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
    public Button buttonUser,rentButton;
    public boolean isLenderChildAdded;
    public boolean isTimeChildAdded;

    private TextView pckUpDate ;
    private TextView pckUpTime ;
    private TextView dropDate ;
    private TextView dropTime;
    private Button startDateBut;
    private Button startTimeBut;
    private Button endDateBut;
    private Button endTimeBut;
    private Button submitBut;

    public int hrSelected;
    public int minSelected;
    public int year;
    public int month;
    public int day;

    public int flagfordate;
    public int flagfortime;

    public User  lender;
    public SharedPreferences pref;
    public String caller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_pickup);
        Intent intent = getIntent();
        caller = intent.getStringExtra("caller");

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
        isLenderChildAdded = false;
        isTimeChildAdded = false;

        assetName.setText(asset.getAssetName());
        assetId.setText(asset.getAssetId());
        assetPickup.setText("Pickup at: "+asset.getPickupLocation());
        assetDrop.setText("Drop at: "+asset.getDropLocation());
        assetCost.setText(asset.getCharges()+"Rs/hr");
        if(asset.getAssetType().compareTo("car")==0){
            assetImg.setImageResource(R.drawable.car);
        }else{
            assetImg.setImageResource(R.drawable.bike);
        }

        pref = PreferenceManager.getDefaultSharedPreferences(AssetPickup.this);


        buttonUser = findViewById(R.id.userButton);
        rentButton = findViewById(R.id.rentButton);

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
        if(pref.getString("admin","").compareTo("yes")==0){
            if(asset.getIsAvail().toLowerCase().compareTo("yes")==0){
                rentButton.setText("Block");
            }else{
                rentButton.setText("UnBlock");
            }
            rentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(rentButton.getText().toString().compareTo("Block")==0){
                        blockAsset("Block");
                        rentButton.setText("UnBlock");
                    }else{
                        blockAsset("UnBlock");
                        rentButton.setText("Block");
                    }

                }
            });
        }else {
            rentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isTimeChildAdded) {
                        inflateTimePickerCard();
                        isTimeChildAdded = true;
                        rentButton.setEnabled(false);
                    }
                }
            });
        }

    }

    public void blockAsset(String block) {
        Log.d("block", "asset");
        GetAssetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetAssetDataService.class);

        Map<String, String> query = new HashMap<>();
        query.put("ASSET_ID", asset.getAssetId());
        query.put("ASSET_NAME", asset.getAssetName());
        query.put("ASSET_TYPE", asset.getAssetType());
        query.put("PICKUP_LOCATION", asset.getPickupLocation());
        query.put("DROP_LOCATION", asset.getDropLocation());
        query.put("CHARGES",asset.getCharges());
        if(block.compareTo("Block")==0){
            query.put("IS_AVAIL","NO");
        }else{
            query.put("IS_AVAIL","YES");
        }



        Call<Asset> call = service.putAssetCheck(query);
        call.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call , Response<Asset> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    Asset data = response.body();

                    if (data==null) {
                        Toast.makeText(AssetPickup.this, "BLOCK FAILED", Toast.LENGTH_LONG).show();

                    } else
                    {
                        Toast.makeText(AssetPickup.this, "BLOCKED", Toast.LENGTH_LONG).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Asset> call, Throwable t) {
                Log.d("FAILED", t.getMessage());
                Toast.makeText(AssetPickup.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void rentAsset() {
        Log.d(TAG, "rent");

        GetRequestDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetRequestDataService.class);

        Date date=new Date(20,02,22);
        Timestamp timestamp = new Timestamp(date.getTime());
        Timestamp myTimeStamp= timestamp;

        Map<String, String> query = new HashMap<>();
        query.put("CUSTOMER_ID", pref.getString(getString(R.string.usr_id),""));
        query.put("ASSET_ID", asset.getAssetId());
        query.put("PICKUP_TIME", pckUpDate.getText().toString()+" "+pckUpTime.getText().toString());
        query.put("DROP_TIME", dropDate.getText().toString()+" "+dropTime.getText().toString());
        Log.d("where", "outside response");


        Call<Request> call = service.postRequestCheck(query);
        call.enqueue(new Callback<Request>() {
            @Override
            public void onResponse(Call<Request> call, Response<Request> response) {
                Log.d("where", "inside response");

                if (response.isSuccessful()) {
                    requestData = response.body();

                    Log.d("Response123", requestData.toString());
                    Toast.makeText(AssetPickup.this , "Request Sent", Toast.LENGTH_LONG).show();



                } else {
                    Log.d("SUCCESS BUT NO DATA", "NO DATA");
                    Toast.makeText(AssetPickup.this , "FAILED", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Request> call, Throwable t) {
                Log.d("FAILED", t.getMessage());

            }
        });

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

        if(flagfortime==1){
            Log.d("start time2",String.valueOf(i)+":"+String.valueOf(i1));
            pckUpTime.setText(getFormattedTimeString(i,i1));
        }else{
            Log.d("end time2",String.valueOf(i)+":"+String.valueOf(i1));
            dropTime.setText(getFormattedTimeString(i,i1));
        }
    }

    public static class TimePickerFragment extends DialogFragment{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

    }

    public static class DatePickerFragment extends DialogFragment
             {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
        }


    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        if(flagfordate==1){
            pckUpDate.setText(getFormattedDateString(day,month,year));
        }else{
            dropDate.setText(getFormattedDateString(day,month,year));
        }
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

    public void inflateUserCard(){
        LinearLayout linearLayout = findViewById(R.id.asset_detail_linear);
        View v = LayoutInflater.from(AssetPickup.this).inflate(R.layout.user_card, null);
        linearLayout.addView(v);
        TextView name = v.findViewById(R.id.lenderName);
        TextView id = v.findViewById(R.id.lenderId);
        TextView contact = v.findViewById(R.id.lenderContact);
        TextView loc = v.findViewById(R.id.lenderLoc);
        name.setText(lender.getName());
        id.setText(lender.getUserName());
        contact.setText("Phno: "+lender.getPhno());
        loc.setText("From: "+lender.getLocation());
    }

    public void inflateTimePickerCard(){
        LinearLayout linearLayout = findViewById(R.id.asset_detail_linear);
        View v = LayoutInflater.from(AssetPickup.this).inflate(R.layout.date_time_selector, null);


        pckUpDate = v.findViewById(R.id.startDate);
        pckUpTime = v.findViewById(R.id.startTime);
        dropDate = v.findViewById(R.id.endDate);
        dropTime = v.findViewById(R.id.endTime);
        startDateBut = v.findViewById(R.id.startDateButton);
        endDateBut = v.findViewById(R.id.endDateButton);
        startTimeBut = v.findViewById(R.id.startTimeButton);
        endTimeBut = v.findViewById(R.id.endTimeButton);
        submitBut = v.findViewById(R.id.requestButton);

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        pckUpDate.setText(getFormattedDateString(day,month,year));
        dropDate.setText(getFormattedDateString(day,month,year));
        Log.d("start time1",String.valueOf(hour)+":"+String.valueOf(minute));
        pckUpTime.setText(getFormattedTimeString(hour,minute));
        Log.d("end time1",String.valueOf(hour)+":"+String.valueOf(minute));
        dropTime.setText(getFormattedTimeString(hour,minute));
        
        startDateBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
                flagfordate = 1;

            }
        });
        endDateBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker2");
                flagfordate = 2;

            }
        });
        startTimeBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "timePicker");
                flagfortime = 1;

            }
        });
        endTimeBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "tiePicker2");
                flagfortime = 2;
            }
        });

        submitBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rentAsset();
                finish();
            }
        });

        linearLayout.addView(v);
    }

    public String getFormattedTimeString(int i,int i1){
        String hr = String.valueOf(i);
        String min = String.valueOf(i1);
        if(i/10==0&&i1/10==0){
            return "0"+hr+":"+"0"+min;
        }else if(i/10==0){
            return "0"+hr+":"+min;
        }else if(i1/10==0){
            return hr+":"+"0"+min;
        }else{
            return hr+":"+min;
        }
    }

    public String getFormattedDateString(int day,int month,int year){
        String format = "%d|%d|%d";
        if(month/10==0&&day/10==0){
            format = "0%d|0%d|%d";
        }else if(month/10==0){
            format = "%d|0%d|%d";
        }else if(day/10==0){
            format = "0%d|%d|%d";
        }

        return String.format(format,day,month,year);

    }

}
