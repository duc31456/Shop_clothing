package com.example.ck;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ck.item_class.cart_item;
import com.example.ck.request_api.CallApiUser;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryActivity extends AppCompatActivity {
    Button buttonpdf, btnthanhtoandonhang;
    ArrayList<cart_item> array = new ArrayList<>();
    EditText recipient_name, recipient_phone, recipient_mail, recipient_adress;
    TextView sanphamgiaohang, total_price, phuongthuc;
    Integer dem = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_delivery);

        anhxa();
        addtoPDF();

      btnthanhtoandonhang.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              thanhtoandonhang();
          }
      });
      buttonpdf.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(DeliveryActivity.this, MainActivity.class);
              startActivity(intent);
          }
      });
    }

    public void anhxa()
    {
         recipient_name = findViewById(R.id.recipient_name);
         recipient_phone = findViewById(R.id.recipient_phone);
         recipient_mail = findViewById(R.id.recipient_email);
         sanphamgiaohang = findViewById(R.id.sanphamgiaohang);
        total_price = findViewById(R.id.total_price);
        phuongthuc = findViewById(R.id.phuongthuc);
        buttonpdf = findViewById(R.id.btnpdf);
        recipient_adress = findViewById(R.id.recipient_adress);
        btnthanhtoandonhang = findViewById(R.id.btnthanhtoandonhang);
    }
    public void deleteAllCart()
    {
        CallApiUser.callApi.deleteAllCart(product_activity.iduser).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) { }

            @Override
            public void onFailure(Call<Void> call, Throwable t) { }
        });
    }
    private void addtoPDF()
    {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            array = (ArrayList<cart_item>) intent.getSerializableExtra("thanhtoan");
            //    Log.d("RECEIPT", array.toString());
            for (int i = 0; i < array.size(); i++)
            {
                dem += (array.get(i).getPrice()*array.get(i).getQuantity());
                sanphamgiaohang.append(array.get(i).getName()+ "\n"
                        +"Giá: "+array.get(i).getPrice()+"\t"+ "Số lượng: "
                        + array.get(i).getQuantity() +"\n");
            }
            total_price.setText(String.valueOf(dem) + " VND");
        }
    }
    public void thanhtoandonhang()
    {
        String tempproduct = "", tempsize = "";
        String tempquatity ="";
        for(int i = 0; i < array.size(); i++)
        {
            if (tempproduct.isEmpty()) {
                tempproduct = array.get(i).getId();
            }else {
                tempproduct = tempproduct + "," + array.get(i).getId();
            }
            if (tempquatity.isEmpty()) {
                tempquatity = array.get(i).getQuantity().toString();
            }else {
                tempquatity = tempquatity + "," + array.get(i).getQuantity().toString();
            }
            if (tempsize.isEmpty()) {
                tempsize = array.get(i).getSize();
            }else {
                tempsize = tempsize + "," + array.get(i).getSize();
            }
        }

        //Log.d("THANHTOAN", tempproduct+"\n"+ tempsize+"\n"+ tempquatity);
        if(recipient_name.getText().toString().isEmpty() ||
                recipient_phone.getText().toString().isEmpty() ||
                recipient_mail.getText().toString().isEmpty() ||
                recipient_adress.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
        }else {
            CallApiUser.callApi.post_ApiOrder(recipient_name.getText().toString(),
                    recipient_phone.getText().toString(),
                    recipient_mail.getText().toString(),
                    recipient_adress.getText().toString(),
                    tempproduct, tempsize, tempquatity,
                    0,dem, product_activity.iduser
            ).enqueue(new Callback<cart_item>() {
                @Override
                public void onResponse(Call<cart_item> call, Response<cart_item> response) {
                    Toast.makeText(DeliveryActivity.this, "Đơn hàng của bạn đặt thành công!", Toast.LENGTH_SHORT).show();
                    deleteAllCart();
                    btnthanhtoandonhang.setVisibility(View.INVISIBLE);
                    buttonpdf.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<cart_item> call, Throwable t) {
                }
            });
        }

    }


}