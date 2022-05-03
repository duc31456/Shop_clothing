package com.example.ck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

public class product_activity extends AppCompatActivity {
    Spinner spinner_size;
    ArrayList<String> size;
    ImageView addtocart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_product);

        spinner_size = findViewById(R.id.spinner_size);
        addtocart = findViewById(R.id.addtocart);
        size = new ArrayList<>();
        size.add("S");
        size.add("M");
        size.add("L");
        ArrayAdapter adapter_size = new ArrayAdapter(product_activity.this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                size);
        spinner_size.setAdapter(adapter_size);

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(product_activity.this, cart_activity.class);
                startActivity(intent);
            }
        });
    }
}