package com.example.acer.rentapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acer.rentapp.AssetPickup;
import com.example.acer.rentapp.R;
import com.example.acer.rentapp.RequestDetails;
import com.example.acer.rentapp.SentReqDetails;
import com.example.acer.rentapp.model.Asset;
import com.example.acer.rentapp.model.Request;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    private List<Request> assets;
    private Context context;
    private String type;

    public RequestAdapter(List<Request> assets, Context context, String type){
        this.assets = assets;
        this.context = context;
        this.type = type;
    }

    @Override
    public RequestAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.request_items,parent,false);
        RequestAdapter.ViewHolder viewHolder = new RequestAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RequestAdapter.ViewHolder holder, int position) {
        final Request asset = assets.get(position);
        holder.name.setText(asset.getAssetId());
        holder.cost.setText(asset.getRent());
        holder.location.setText(asset.getCustomerId());
        if(asset.getStatus().compareTo("pending")==0){
            holder.img.setImageResource(R.drawable.cross);
        }else{
            holder.img.setImageResource(R.drawable.tick);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if(type.compareTo("received")==0){
                    intent = new Intent(context,RequestDetails.class);
                }else{
                    intent = new Intent(context,SentReqDetails.class);
                }

                intent.putExtra("Asset",asset);
                context.startActivity(intent);
            }
        });
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
        public View itemView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.list_text_name);
            this.cost = (TextView) itemView.findViewById(R.id.cost);
            this.location  = (TextView) itemView.findViewById(R.id.Location);
            this.img = (ImageView) itemView.findViewById(R.id.logo);
            this.itemView = itemView;
        }
    }



}
