package com.example.acer.rentapp.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.acer.rentapp.MyAssetActivity;
import com.example.acer.rentapp.R;
import com.example.acer.rentapp.RentalListActivity;
import com.example.acer.rentapp.SeeRequestActivity;
import com.example.acer.rentapp.UserDetailsActivity;


public class HomeFragment extends Fragment {

    public ImageButton carImgButton;
    public ImageButton logout;
    public ImageButton editDetailsButton;
    public ImageButton recRequestBut;
    public ImageButton sentRequestBut;
    public ImageButton myAssetBut;

    public HomeFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        AppBarLayout appbar = (AppBarLayout) v.findViewById(R.id.appbar);
        float heightDp = getResources().getDisplayMetrics().heightPixels *2/ 5;
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        lp.height = (int) heightDp;

        carImgButton = v.findViewById(R.id.carButton);
        logout = v.findViewById(R.id.logout);
        editDetailsButton = v.findViewById(R.id.editDetails);
        recRequestBut = v.findViewById(R.id.received);
        sentRequestBut = v.findViewById(R.id.sent);
        myAssetBut = v.findViewById(R.id.myasset);

        myAssetBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MyAssetActivity.class);
                startActivity(intent);
            }
        });

        carImgButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RentalListActivity.class);
                intent.putExtra("type","car");
                startActivity(intent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.usr_id), "");
                editor.putString(getString(R.string.usr_name), "");
                editor.putString(getString(R.string.usr_loc), "");
                editor.putString(getString(R.string.usr_cont), "");
                editor.putString(getString(R.string.password), "");
                editor.apply();
                getActivity().finish();
            }
        });

        editDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startUserDetailsActivity();
            }
        });

        recRequestBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SeeRequestActivity.class);
                intent.putExtra("type","received");
                startActivity(intent);
            }
        });

        sentRequestBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),SeeRequestActivity.class);
                intent.putExtra("type","sent");
                startActivity(intent);
            }
        });

        return v;
    }

    public void startUserDetailsActivity(){
        Intent intent = new Intent(getActivity(),UserDetailsActivity.class);
        startActivity(intent);
    }


}

