package com.example.ck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

public class Welcome extends AppCompatActivity {
    TextView welcome_shop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        welcome_shop = findViewById(R.id.welcome_shop);
        FirebaseApp.initializeApp(this);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation_welcome);
        welcome_shop.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Welcome.this, login_activity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}