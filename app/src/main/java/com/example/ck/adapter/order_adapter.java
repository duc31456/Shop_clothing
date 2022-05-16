package com.example.ck.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ck.R;
import com.example.ck.directggmap.GMapV2Direction;
import com.example.ck.fragment.fragment_home;
import com.example.ck.fragment.fragment_lichsumuahang;
import com.example.ck.fragment.fragment_map;
import com.example.ck.item_class.productModel.class_product;
import com.example.ck.item_class.productModel.class_review;
import com.example.ck.item_class.userModel.class_order;
import com.example.ck.product_activity;
import com.example.ck.request_api.CallApiUser;
import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class order_adapter extends RecyclerView.Adapter<order_adapter.order_viewHolder>{

    private fragment_lichsumuahang context;
    private ArrayList<class_order> orders;

    public order_adapter(fragment_lichsumuahang context) {
        this.context = context;
    }
    public String TENSP ="";
    public String IDSP ="";
    public float rate;
    public String freeback ="";
    public void setdata(ArrayList<class_order> list_order)
    {
        this.orders = list_order;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public order_viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new order_viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull order_viewHolder holder, int position) {
            class_order list = orders.get(position);
        if(list == null)
        {
            return;

        }
        else {
            IDSP = list.getId();
            holder.thongso.setText("Đơn hàng thứ " + position);
                holder.nguoidat.append(list.getRecipient_name());
                holder.sdt.append(list.getRecipient_phone());
            holder.email.append(list.getRecipient_email());
            holder.diachi.append(list.getRecipient_address());
            holder.trangthai.append(list.getStatus());
            holder.tonggia.append(String.valueOf(list.getTotal_price()) + " VND");

               for(int a = 0; a < list.getProducts().size(); a++)
               {
                   holder.thongtinsanpham.append("ID sản phẩm: "+list.getProducts().get(a).getId()+"\n"
                           +"Size: "+list.getProducts().get(a).getSize()+"\t" + "Quantity: "+
                           list.getProducts().get(a).getQuantity() +"\n");
               }

            holder.foldingCell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.foldingCell.toggle(false);
                }
            });
            holder.btnxem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
            holder.btnxem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.guidanhgia.setVisibility(View.VISIBLE);
                    holder.nhapdanhgia.setVisibility(View.VISIBLE);
                    holder.rating.setVisibility(View.VISIBLE);
                }
            });
            holder.guidanhgia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.guidanhgia.setVisibility(View.INVISIBLE);
                    holder.nhapdanhgia.setVisibility(View.INVISIBLE);
                    holder.rating.setVisibility(View.INVISIBLE);
                    CallApiReview();
                }
            });
            holder.rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    rate = v;
                }
            });
            freeback = holder.nhapdanhgia.getText().toString();
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class order_viewHolder extends RecyclerView.ViewHolder
    {
        private FoldingCell foldingCell;
        private TextView nguoidat, sdt, email, diachi, trangthai, tonggia, thongso, thongtinsanpham, btnxem;
        private Button guidanhgia;
        private EditText nhapdanhgia;
        private RatingBar rating;
        public order_viewHolder(@NonNull View itemView) {
            super(itemView);

            foldingCell = itemView.findViewById(R.id.folding_cell);
            nguoidat = itemView.findViewById(R.id.nguoidat);
            sdt = itemView.findViewById(R.id.sdt);
            email = itemView.findViewById(R.id.email);
            diachi = itemView.findViewById(R.id.diachi);
            trangthai = itemView.findViewById(R.id.trangthai);
            tonggia = itemView.findViewById(R.id.tonggia);
            thongso = itemView.findViewById(R.id.thongso);
            btnxem = itemView.findViewById(R.id.btnxem);
            thongtinsanpham = itemView.findViewById(R.id.thongtinsanpham);
            guidanhgia = itemView.findViewById(R.id.guidanhgia);
            nhapdanhgia = itemView.findViewById(R.id.nhapdanhgia);
            rating = itemView.findViewById(R.id.rating);

            guidanhgia.setVisibility(View.INVISIBLE);
            nhapdanhgia.setVisibility(View.INVISIBLE);
            rating.setVisibility(View.INVISIBLE);
        }
    }

    private void CallApiReview()
    {
        CallApiUser.callApi.post_ApiReview(IDSP, product_activity.iduser,
                String.valueOf(rate), freeback
                ).enqueue(new Callback<class_review>() {
            @Override
            public void onResponse(Call<class_review> call, Response<class_review> response) {
                Toast.makeText(context.getActivity(), "Đánh giá thành công!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<class_review> call, Throwable t) {

            }
        });
    };
}
