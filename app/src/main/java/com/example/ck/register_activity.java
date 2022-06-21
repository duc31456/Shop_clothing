package com.example.ck;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ck.item_class.userModel.class_account;
import com.example.ck.item_class.userModel.class_user;
import com.example.ck.request_api.CallApiUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class register_activity extends AppCompatActivity {
    EditText input_username, input_fullname, input_email, input_phone, input_password, input_confirmpass;
    Button btndangky;
    Dialog dialog;
    FirebaseAuth mAuth;
    String verifyId;
    String invalid_phone = "+84";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);
        input_username = findViewById(R.id.input_username);
        input_fullname = findViewById(R.id.input_fullname);
        input_email = findViewById(R.id.input_email);
        input_phone = findViewById(R.id.input_phone);
        input_password = findViewById(R.id.input_password);
        input_confirmpass = findViewById(R.id.input_confirmpass);
        btndangky = findViewById(R.id.btndangky);
        btndangky.setEnabled(false);
        mAuth = FirebaseAuth.getInstance();
        input_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(isValidEmailId(input_email.getText().toString().trim())){
                    Toast.makeText(getApplicationContext(), "Địa chỉ email hợp lệ!", Toast.LENGTH_SHORT).show();
                    btndangky.setEnabled(true);
                }else{
                 //   Toast.makeText(getApplicationContext(), "Địa chỉ email không hợp lệ!", Toast.LENGTH_SHORT).show();
                    btndangky.setEnabled(false);
                }
            }
        });
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(input_username.getText().toString().isEmpty() ||
                        input_fullname.getText().toString().isEmpty() ||
                        input_email.getText().toString().isEmpty() ||
                        input_phone.getText().toString().isEmpty() ||
                        input_password.getText().toString().isEmpty() ||
                        input_confirmpass.getText().toString().isEmpty())
                {
                    Toast.makeText(register_activity.this,
                            "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show();
                }
                else {
                        if (input_password.getText().toString().equals(input_confirmpass.getText().toString()))
                        {
                            String temp = invalid_phone.concat(input_phone.getText().toString());
                            if(temp.length() < 10){
                                input_phone.setError("Enter a valid mobile");
                                input_phone.requestFocus();
                                return;
                            }
                            else {
                                Toast.makeText(register_activity.this, "Nhập OTP xác nhận sdt!", Toast.LENGTH_SHORT).show();
                                sendVerificationCode(temp);
                            }
                        }
                        else
                        {
                            Toast.makeText(register_activity.this,
                                    "Mật khẩu xác nhận sai!", Toast.LENGTH_SHORT).show();
                        }

                }
            }
        });
    }
    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
    public void dialog_nhapotp()
    {
        dialog = new Dialog(register_activity.this);
        dialog.setContentView(R.layout.layout_otp);
        dialog.getWindow().setLayout(1100, 500);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

        EditText nhapotp = dialog.findViewById(R.id.nhapotp);
        Button xacnhan = dialog.findViewById(R.id.xacthuc);

        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyId, nhapotp.getText().toString());
                signInWithPhoneAuthCredential(credential);
            }
        });

    }
    private void sendVerificationCode(String mobile) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mobile)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Log.d("OTP", e.getMessage());
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId,
                                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                                super.onCodeSent(verificationId, token);
                                Log.d("OTP", "Code đã gửi:" + verificationId);
                                verifyId = verificationId;
                                dialog_nhapotp();
                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("OTP", "signInWithCredential:success");
                            dialog.dismiss();
                            Toast.makeText(register_activity.this,
                                    "Xác thực sdt thành công!", Toast.LENGTH_SHORT).show();
                            dangkytaikhoan();
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.d("OTP", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Log.d("OTP", "OTP không hợp lệ", task.getException());

                            }
                        }
                    }
                });
    }
    public void dangkytaikhoan()
    {
       CallApiUser.callApi.post_ApiUser(input_username.getText().toString(),
               input_email.getText().toString(),
               input_phone.getText().toString(),
               input_password.getText().toString(),
               input_confirmpass.getText().toString(),
               input_fullname.getText().toString(), "user").enqueue(new Callback<class_user>() {
            @Override
           public void onResponse(Call<class_user> call, Response<class_user> response) {
                if (response.isSuccessful())
                {
                    new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(register_activity.this,
                                "Đăng ký tài khoản thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(register_activity.this, login_activity.class);
                        startActivity(intent);
                        finish();
                    }
                },1000);
                }
            }

            @Override
            public void onFailure(Call<class_user> call, Throwable t) {
                Log.d("DK", t+"");
            }
        });
    }
}