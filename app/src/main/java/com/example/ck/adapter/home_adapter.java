package com.example.ck.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ck.R;
import com.example.ck.fragment.fragment_home;
import com.example.ck.item_class.class_home;

import java.util.ArrayList;

public class home_adapter extends RecyclerView.Adapter<home_adapter.home_viewHolder>{
    private fragment_home context;
    private ArrayList<class_home> homes;

    public home_adapter(fragment_home context) {
        this.context = context;
    }

    public void setdata(ArrayList<class_home> listhome)
    {
        this.homes = listhome;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public home_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listhome, parent, false);
        return new home_viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull home_viewHolder holder, int position) {
        class_home list = homes.get(position);
        if(list == null)
        {
            return;
        }
        else {
            holder.hinh.setImageResource(list.getHinh());
            holder.mota.setText(list.getMota());
        }
    }

    @Override
    public int getItemCount() {
        return homes.size();
    }

    public class home_viewHolder extends RecyclerView.ViewHolder
    {
        private ImageView hinh;
        private TextView mota;
        public home_viewHolder(@NonNull View itemView) {
            super(itemView);

            hinh = itemView.findViewById(R.id.image_home);
            mota = itemView.findViewById(R.id.mota_imagehome);
        }
    }
}
