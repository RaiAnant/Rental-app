package com.example.acer.rentapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.acer.rentapp.AdminsUserView;
import com.example.acer.rentapp.R;
import com.example.acer.rentapp.RentalListActivity;

public class AdminHomeFragment extends Fragment {
    public ImageButton assetImgButton;
    public ImageButton logout;
    public ImageButton profileImgButton;


    public AdminHomeFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_home,  container, false);
        AppBarLayout appbar = (AppBarLayout) v.findViewById(R.id.appbar);
        float heightDp = getResources().getDisplayMetrics().heightPixels / 3;
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
        lp.height = (int) heightDp;
        assetImgButton = v.findViewById(R.id.asset);
        logout = v.findViewById(R.id.logout);
        profileImgButton = v.findViewById(R.id.profile);
        assetImgButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RentalListActivity.class);
                intent.putExtra("type","any");
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
                editor.putString("admin","");
                editor.apply();
                getActivity().finish();
            }
        });
        profileImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AdminsUserView.class);
                startActivity(intent);
            }
        });
        return v;
    }

}
