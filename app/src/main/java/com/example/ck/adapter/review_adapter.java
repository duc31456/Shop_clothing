package com.example.ck.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ck.R;
import com.example.ck.fragment.fragment_home;
import com.example.ck.item_class.productModel.class_product;
import com.example.ck.item_class.productModel.class_review;
import com.example.ck.product_activity;

import java.util.ArrayList;

public class review_adapter extends RecyclerView.Adapter<review_adapter.review_viewHolder>{
    private product_activity context;
    private ArrayList<class_review> reviews;

    public review_adapter(product_activity context) {
        this.context = context;
    }

    public void setdata(ArrayList<class_review> listreview) {
        this.reviews = listreview;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public review_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_freeback, parent, false);
        return new review_adapter.review_viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull review_viewHolder holder, int position) {
        class_review list = reviews.get(position);
        if (list == null) {
            return;
        } else{
            holder.ten.setText(list.getUsername());
            holder.danhgia.setText(String.valueOf(list.getRate()));
            holder.chitietdanhgia.setText(list.getFeedback());
            holder.ngaybinhluan.setText(list.getCreated_at());
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class review_viewHolder extends RecyclerView.ViewHolder {
        private TextView ten, danhgia, chitietdanhgia, ngaybinhluan;

        public review_viewHolder(@NonNull View itemView) {
            super(itemView);

            ten = itemView.findViewById(R.id.name_freeback);
            danhgia = itemView.findViewById(R.id.rate_freeback);
            chitietdanhgia = itemView.findViewById(R.id.detail_freeback);
            ngaybinhluan = itemView.findViewById(R.id.created_at);
        }
    }
}
