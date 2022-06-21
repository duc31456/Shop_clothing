package com.example.ck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ck.item_class.userModel.class_user;
import com.example.ck.request_api.CallApiUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login_activity extends AppCompatActivity{
    TextView btnregister;
    Button btnlogin;
    EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        btnregister = findViewById(R.id.btnregister);
        btnlogin = findViewById(R.id.btnlogin);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

      btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login_activity.this, register_activity.class);
                startActivity(intent);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callApiUser();
            }
        });
    }

    //đăng nhập
    public void callApiUser() {
        CallApiUser.callApi.login_ApiUser(username.getText().toString().trim(),
                password.getText().toString().trim()).enqueue(new Callback<class_user>() {
            @Override
            public void onResponse(Call<class_user> call, Response<class_user> response) {
                class_user users = response.body();
                if(response.isSuccessful() && users != null)
                {
                    try {
                        MainActivity.firstname = username.getText().toString();
                        product_activity.iduser = users.getId().trim();
                        MainActivity.image = users.getLinkAvt().trim();
                        product_activity.userNameReview = users.getUsername().trim();
                      //  Log.d("ERRORLOGIN", MainActivity.image);
                    }catch (Exception e)
                    { }finally {
                        Toast.makeText(login_activity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(login_activity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    return;
                }
                Toast.makeText(login_activity.this, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<class_user> call, Throwable t) {

            }
        });
    }
}