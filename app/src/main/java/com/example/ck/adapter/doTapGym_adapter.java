package com.example.ck.adapter;

import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ck.R;
import com.example.ck.fragment.fragment_dobongda;
import com.example.ck.fragment.fragment_dotapgym;
import com.example.ck.item_class.productModel.class_product;
import com.example.ck.product_activity;
import com.example.ck.request_api.CallApiUser;

import java.util.ArrayList;

public class doTapGym_adapter extends RecyclerView.Adapter<doTapGym_adapter.tapgym_viewHolder> {
    private fragment_dotapgym context;
    private ArrayList<class_product> dotapgym;

    public doTapGym_adapter(fragment_dotapgym context) {
        this.context = context;
    }

    public void setdata(ArrayList<class_product> list)
    {
        this.dotapgym = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public tapgym_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listcategory, parent, false);
        return new tapgym_viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull tapgym_viewHolder holder, int position) {
        class_product list = dotapgym.get(position);
        if(list == null)
        {
            return;

        }
        else {
            // thêm gạch cho giá ban đầu chưa khuyến mãi
            holder.giabandau.setPaintFlags(holder.giabandau.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.mota.setText(list.getName());
            String url = "http://"+ CallApiUser.LOCALHOST+":3000/api/v1/products/"+list.getId()+"/image";
            Glide.with(context).load(url).into(holder.hinh);
            holder.giabandau.setText(String.valueOf(list.getPrice()));
            holder.giaban.setText(String.valueOf(list.getPrice()-(list.getDiscount()*100)));
            holder.giamgia.setText("-"+list.getDiscount()+"%");
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
        return dotapgym.size();
    }

    public class tapgym_viewHolder extends RecyclerView.ViewHolder
    {
        private ImageView hinh;
        private TextView mota, giaban, giabandau, giamgia;

        public tapgym_viewHolder(@NonNull View itemView) {
            super(itemView);

            hinh = itemView.findViewById(R.id.image_home);
            mota = itemView.findViewById(R.id.mota_imagehome);
            giaban = itemView.findViewById(R.id.gia_home);
            giabandau = itemView.findViewById(R.id.gia2_home);
            giamgia = itemView.findViewById(R.id.discount);
        }
    }
}
