package com.example.ck;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ck.adapter.giohang_adapter;
import com.example.ck.adapter.home_adapter;
import com.example.ck.item_class.cart_item;
import com.example.ck.item_class.productModel.class_product;
import com.example.ck.item_class.userModel.class_cart;
import com.example.ck.item_class.userModel.class_user;
import com.example.ck.request_api.CallApiUser;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cart_activity extends AppCompatActivity {
    ImageView btntrangchu, btn_back, btndelete;
    Button thanhtoan;
    TextView giatien, ten_sanpham, soluong, size, text_cart;
    GifImageView gif_cart;
    public RecyclerView recyclerView;
    public static giohang_adapter adapter;
    public static ArrayList<cart_item>cart_user = new ArrayList<>();
    public static Integer TONGGIATRI = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cart);

        anhxa();
        add_itemcart();
        load_danhsachcart();
        btntrangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cart_activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cart_activity.this, DeliveryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("thanhtoan", cart_user);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });

    }

    public void add_itemcart()
    {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String id_sp = bundle.getString("id_sp", "1234");
            String ten_sp = bundle.getString("ten_sp", "1234");
            Integer soluong = bundle.getInt("soluong", 1);
            String size = bundle.getString("size", "1234");
            Integer gia_sp = bundle.getInt("gia_sp", 1);
            cart_user.add(new cart_item(id_sp, ten_sp, soluong, size, gia_sp));
            TONGGIATRI = TONGGIATRI + (gia_sp * soluong);
        }

    }
    public void load_danhsachcart()
    {
        adapter = new giohang_adapter(cart_activity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(cart_activity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter.setdata(cart_user);
        if(cart_user != null)
        {
            text_cart.setVisibility(View.GONE);
            gif_cart.setVisibility(View.GONE);
            recyclerView.setAdapter(adapter);

        }
        else
        {
            TONGGIATRI = 0;

            text_cart.setVisibility(View.VISIBLE);
            gif_cart.setVisibility(View.VISIBLE);
        }
    }
    public void anhxa()
    {
        btntrangchu = findViewById(R.id.btntrangchu);
        btn_back = findViewById(R.id.btn_back);
        thanhtoan =findViewById(R.id.thanhtoan);
        btndelete = findViewById(R.id.delete);
        giatien = findViewById(R.id.textView9);
        ten_sanpham = findViewById(R.id.ten_sanpham);
        soluong = findViewById(R.id.soluong);
        size = findViewById(R.id.textView2);
        text_cart = findViewById(R.id.text_cart);
        gif_cart = findViewById(R.id.gif_cart);
        recyclerView = findViewById(R.id.list_cart);
        recyclerView.setFocusable(false);
        text_cart.setVisibility(View.VISIBLE);
        gif_cart.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}