package com.example.ck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ck.adapter.home_adapter;
import com.example.ck.adapter.review_adapter;
import com.example.ck.item_class.productModel.class_product;
import com.example.ck.item_class.productModel.class_review;
import com.example.ck.item_class.userModel.class_cart;
import com.example.ck.item_class.userModel.class_user;
import com.example.ck.request_api.CallApiUser;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class product_activity extends AppCompatActivity {
    Spinner spinner_size;
    ArrayList<String> size;
    ImageView addtocart, image_freeback, btn_back;
    TextView tensp, gia, mota, soluong;
    ImageView anhsp, tangsoluong, giamsoluong;
    ConstraintLayout background;
    public review_adapter adapter;

    public static String iduser = "1";

    public String SIZE ="";

    ArrayList<class_product> product = new ArrayList<>();
    public static String idProduct = "";
    public static String userNameReview ="";

    private int soluongsp = 1;

    EditText input_review;
    RatingBar rate_review;
    public int rate_score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_product);

        Anhxa();
        adapter = new review_adapter(this);
        LoadSanPham();
        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallApiAddToCart();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        image_freeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(product_activity.this);
                dialog.setContentView(R.layout.dialog_freeback);
                dialog.getWindow().setLayout(1050, 2000);
                dialog.show();

                input_review = dialog.findViewById(R.id.input_review);
                rate_review = dialog.findViewById(R.id.rate_review);
                Button send_review = dialog.findViewById(R.id.send_review);

                RecyclerView recyclerView = dialog.findViewById(R.id.list_review);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getParent(), RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(adapter);

                rate_review.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        rate_score = (int) v;
                    }
                });
                send_review.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(input_review.getText().toString().isEmpty())
                        {
                            Toast.makeText(product_activity.this, "Mời bạn đánh giá sản phẩm!", Toast.LENGTH_SHORT).show();
                        }else {
                            CallApiInsertReview();
                            LoadSanPham();
                            send_review.setEnabled(false);
                            input_review.setText("");
                        }
                    }
                });
            }
        });
        tangsoluong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soluongsp = soluongsp + 1;
                soluong.setText(String.valueOf(soluongsp));
                if(soluongsp > 1)
                {
                    giamsoluong.setEnabled(true);
                }
            }
        });
        giamsoluong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soluongsp = soluongsp - 1;
                soluong.setText(String.valueOf(soluongsp));
                if(soluongsp <= 1)
                {
                    soluongsp = 1;
                    giamsoluong.setEnabled(false);
                }
            }
        });
    }


    public void CallApiInsertReview()
    {
        CallApiUser.callApi.updatereview(idProduct, userNameReview,
                rate_score, input_review.getText().toString().trim()).enqueue(new Callback<class_review>() {
            @Override
            public void onResponse(Call<class_review> call, Response<class_review> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(product_activity.this,
                            response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<class_review> call, Throwable t) {
            }
        });
    }

    private void Anhxa()
    {
        spinner_size = findViewById(R.id.spinner_size);
        image_freeback = findViewById(R.id.freeback);
        addtocart = findViewById(R.id.addtocart);
        btn_back = findViewById(R.id.btn_back);
        tensp = findViewById(R.id.textView4);
        gia = findViewById(R.id.textView5);
        mota = findViewById(R.id.textView6);
        anhsp = findViewById(R.id.imageView);
        soluong = findViewById(R.id.textView7);
        tangsoluong = findViewById(R.id.imageView4);
        giamsoluong = findViewById(R.id.imageView3);
        background = findViewById(R.id.background);
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        background.setBackgroundColor(color);

        size = new ArrayList<>();
        size.add("S");
        size.add("M");
        size.add("L");
        ArrayAdapter adapter_size = new ArrayAdapter(product_activity.this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                size);
        spinner_size.setAdapter(adapter_size);

        spinner_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SIZE = size.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void LoadSanPham()
    {
        CallApiUser.callApi.loadSanPham(idProduct).enqueue(new Callback<ArrayList<class_product>>() {
            @Override
            public void onResponse(Call<ArrayList<class_product>> call, Response<ArrayList<class_product>> response) {

                    product = response.body();
                    if(response.isSuccessful())
                    {
                        tensp.setText(product.get(0).getName());
                        gia.setText(String.valueOf(product.get(0).getPrice()-(product.get(0).getDiscount()*100)) +" VND");
                        mota.setText(product.get(0).getDescription());
                        adapter.setdata(product.get(0).getReview());
                        cart_activity.link_sanpham = product.get(0).getLink_image();
                        try {
                            byte[] decodedString = Base64.decode(product.get(0).getLink_image().substring(22), Base64.DEFAULT);
                            // Glide.with(context).load(decodedString).into(holder.hinh);
                            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                            anhsp.setImageBitmap(decodedByte);
                        }catch (Exception e)
                        {
                            //Log.d("ERRORLOGIN", e+"");
                        }
                    }
            }

            @Override
            public void onFailure(Call<ArrayList<class_product>> call, Throwable t) {

            }
        });
    }

        private void CallApiAddToCart()
        {
        CallApiUser.callApi.updateGioHang(iduser, idProduct, SIZE, Integer.parseInt(soluong.getText().toString())).enqueue(new Callback<class_cart>() {
            @Override
            public void onResponse(Call<class_cart> call, Response<class_cart> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(product_activity.this, "Thêm vào giỏ hàng thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(product_activity.this, cart_activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id_sp", idProduct.trim());
                    bundle.putString("ten_sp", tensp.getText().toString().trim());
                    bundle.putInt("soluong", Integer.parseInt(soluong.getText().toString()));
                    bundle.putString("size", SIZE);
                    String[] a = gia.getText().toString().split(" ");
                    bundle.putInt("gia_sp", Integer.parseInt(a[0].trim()));
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void onFailure(Call<class_cart> call, Throwable t) {
                Log.d("BBB",""+t);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}