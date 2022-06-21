package com.example.ck.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ck.MainActivity;
import com.example.ck.R;
import com.example.ck.fragment.fragment_home;
import com.example.ck.item_class.productModel.class_product;
import com.example.ck.login_activity;
import com.example.ck.product_activity;
import com.example.ck.request_api.CallApiUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class home_adapter extends RecyclerView.Adapter<home_adapter.home_viewHolder> {
    private fragment_home context;
    private ArrayList<class_product> homes;

    public home_adapter(fragment_home context) {
        this.context = context;
    }

    public void setdata(ArrayList<class_product> listhome) {
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
        class_product list = homes.get(position);
        if (list == null) {
            return;

        } else {
            // thêm gạch cho giá ban đầu chưa khuyến mãi
            holder.giabandau.setPaintFlags(holder.giabandau.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.mota.setText(list.getName());
            Log.d("ERRORLOGIN", list.getLink_image()+"");
            try {
                byte[] decodedString = Base64.decode(list.getLink_image().substring(23), Base64.DEFAULT);
                // Glide.with(context).load(decodedString).into(holder.hinh);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.hinh.setImageBitmap(decodedByte);
            }catch (Exception e)
            {
                //Log.d("ERRORLOGIN", e+"");
            }
            holder.giabandau.setText(String.valueOf(list.getPrice()));
            holder.giaban.setText(String.valueOf(list.getPrice() - (list.getDiscount() * 100)));
            holder.giamgia.setText("-" + list.getDiscount() + "%");
            holder.hinh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    product_activity.idProduct = list.getId().trim();
                    Intent intent = new Intent(context.getActivity(), product_activity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return homes.size();
    }

    public class home_viewHolder extends RecyclerView.ViewHolder {
        private ImageView hinh;
        private TextView mota, giaban, giabandau, giamgia;

        public home_viewHolder(@NonNull View itemView) {
            super(itemView);

            hinh = itemView.findViewById(R.id.image_home);
            mota = itemView.findViewById(R.id.mota_imagehome);
            giaban = itemView.findViewById(R.id.gia_home);
            giabandau = itemView.findViewById(R.id.gia2_home);
            giamgia = itemView.findViewById(R.id.discount);
        }
    }

}
