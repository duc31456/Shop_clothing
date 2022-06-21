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
import com.example.ck.cart_activity;
import com.example.ck.fragment.fragment_home;
import com.example.ck.item_class.cart_item;
import com.example.ck.item_class.productModel.class_product;
import com.example.ck.item_class.userModel.class_cart;
import com.example.ck.item_class.userModel.class_order;
import com.example.ck.item_class.userModel.class_user;
import com.example.ck.product_activity;
import com.example.ck.request_api.CallApiUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class giohang_adapter extends RecyclerView.Adapter<giohang_adapter.giohang_viewHolder>{
    private cart_activity context;
    private ArrayList<cart_item> cart;

    public giohang_adapter(cart_activity context) {
        this.context = context;
    }

    public void setdata(ArrayList<cart_item> list_cart)
    {
        this.cart = list_cart;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public giohang_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new giohang_viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull giohang_viewHolder holder, int position) {
        Integer vitri = position;
        cart_item list = cart.get(position);
        if(list == null)
        {
            return;
        }
        else {
                    holder.soluong.setText(String.valueOf(list.getQuantity()));
                    holder.size.setText(list.getSize());
                    holder.ten.setText(list.getName());
                    holder.gia.setText((list.getPrice() * list.getQuantity()) +" VND");
           try {
                byte[] decodedString = Base64.decode(list.getLink_image().substring(23), Base64.DEFAULT);
                // Glide.with(context).load(decodedString).into(holder.hinh);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.hinh.setImageBitmap(decodedByte);
            }catch (Exception e)
            {
                Log.d("ERRORLOGIN", e+"");
            }
                    holder.btndelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            cart.remove(holder.getAdapterPosition());
                            notifyItemRemoved(holder.getAdapterPosition());
                            CallApiXoaItemCart(holder.getAdapterPosition());
                        }
                    });
                }

        }
    private void CallApiXoaItemCart(Integer vitri)
    {
        CallApiUser.callApi.deleteBook(vitri).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
    @Override
    public int getItemCount() {
        return cart.size();
    }

    public class giohang_viewHolder extends RecyclerView.ViewHolder
    {
        private ImageView hinh, btndelete;
        private TextView ten, gia, soluong, size;

        public giohang_viewHolder(@NonNull View itemView) {
            super(itemView);

            hinh = itemView.findViewById(R.id.imageView7);
            ten = itemView.findViewById(R.id.ten_sanpham);
            gia = itemView.findViewById(R.id.textView9);
            soluong = itemView.findViewById(R.id.soluong);
            size = itemView.findViewById(R.id.textView2);
            btndelete = itemView.findViewById(R.id.delete);
        }
    }
}
