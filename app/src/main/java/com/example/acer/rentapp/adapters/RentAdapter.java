package com.example.acer.rentapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.rentapp.R;
import com.example.acer.rentapp.model.Asset;
import com.example.acer.rentapp.model.User;

import java.util.List;

public class RentAdapter extends RecyclerView.Adapter<RentAdapter.ViewHolder> {

    private List<Asset> assets;

    public RentAdapter(List<Asset> assets){
        this.assets = assets;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.rent_list_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Asset asset = assets.get(position);
        holder.name.setText(asset.getAssetName());
        holder.cost.setText(asset.getCharges());
        holder.location.setText(asset.getPickupLocation());
        if(asset.getAssetType().compareTo("Car")==0){
            holder.img.setImageResource(R.drawable.bike);
        }else{
            holder.img.setImageResource(R.drawable.car);
        }
    }

    @Override
    public int getItemCount() {
        return assets.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView cost;
        public TextView location;
        public ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.list_text_name);
            this.cost = (TextView) itemView.findViewById(R.id.cost);
            this.location  = (TextView) itemView.findViewById(R.id.Location);
            this.img = (ImageView) itemView.findViewById(R.id.logo);
        }
    }



}
