package com.example.ck.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ck.MainActivity;
import com.example.ck.R;
import com.example.ck.adapter.home_adapter;
import com.example.ck.adapter.order_adapter;
import com.example.ck.item_class.productModel.class_product;
import com.example.ck.item_class.userModel.class_order;
import com.example.ck.product_activity;
import com.example.ck.register_activity;
import com.example.ck.request_api.CallApiUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_lichsumuahang extends Fragment {
    private RecyclerView recyclerView;
    private order_adapter adapter;
    private TextView btngopy, txtmailgui;
    private EditText txtmailnhan, txtthongtin;
    private Button btnsendmail;
    public  String MAIL = "enlan0102@gmail.com";
    ArrayList<class_order> orders = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lichsumuahang, container, false);

        recyclerView = view.findViewById(R.id.list_order);
        btngopy = view.findViewById(R.id.btn7);
        adapter = new order_adapter(this);
        btngopy.setPaintFlags(btngopy.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setFocusable(false);
        CallApiOrder();

        btngopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.layout_mail);
                dialog.getWindow().setLayout(1100, 1800);
                dialog.show();

                txtmailgui = dialog.findViewById(R.id.txtmailgui);
                txtmailnhan = dialog.findViewById(R.id.txtmailnhan);
                txtthongtin = dialog.findViewById(R.id.txtthongtin);
                btnsendmail = dialog.findViewById(R.id.btnsendmail);
               // dialog.setCanceledOnTouchOutside(false);
                txtmailgui.setText(MAIL);
                txtmailgui.setEnabled(false);
                txtmailnhan.setText("desomqua@gmail.com");
                btnsendmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sendEmail();
                        dialog.dismiss();
                    }
                });
            }
        });
        return view;
    }

    private void CallApiOrder()
    {
        CallApiUser.callApi.getApiOrder().enqueue(new Callback<ArrayList<class_order>>() {
            @Override
            public void onResponse(Call<ArrayList<class_order>> call, Response<ArrayList<class_order>> response) {
                orders = response.body();
                Log.d("ORDER", orders.toString());
                adapter.setdata(orders);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<class_order>> call, Throwable t) {

            }
        });
    }
    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {txtmailgui.getText().toString()};
        String[] CC = {txtmailnhan.getText().toString()};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Tiêu đề");
        emailIntent.putExtra(Intent.EXTRA_TEXT, txtthongtin.getText().toString());

        try {
            startActivity(Intent.createChooser(emailIntent, "Đang gửi..."));
            return;
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(), "Không có mail...", Toast.LENGTH_SHORT).show();
        }
    }

}
