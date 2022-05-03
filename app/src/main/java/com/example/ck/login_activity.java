package com.example.ck;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ck.item_class.class_user;
import com.example.ck.request_api.CallApiUser;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login_activity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    TextView btnregister;
    Button btnlogin;
    EditText username,password;
    SignInButton btnlogingoogle;

    GoogleSignInClient googleSignInClient;
     private static final Integer SIGN_IN = 1;
     private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        btnregister = findViewById(R.id.btnregister);
        btnlogin = findViewById(R.id.btnlogin);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnlogingoogle = findViewById(R.id.btnlogingoogle);
        //set size cho button logingoogle
        btnlogingoogle.setSize(SignInButton.SIZE_STANDARD);

        // yêu cầu người dùng cung cấp mail, tên và hình ảnh
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
       // auth = FirebaseAuth.getInstance();
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

        btnlogingoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = googleSignInClient.getSignInIntent();
                startActivityForResult(intent,SIGN_IN);
            }
        });

    }

    public void callApiUser() {
        CallApiUser.callApi.get_ApiUser().enqueue(new Callback<List<class_user>>() {
            @Override
            public void onResponse(Call<List<class_user>> call, Response<List<class_user>> response) {
                List<class_user> users = response.body();
                if (response != null) {
                   if(!username.getText().toString().isEmpty() || !password.getText().toString().isEmpty())
                   {
                       for (int i = 0; i < users.size(); i++)
                       {
                           if (username.getText().toString().contains(users.get(i).getAccount().getUsername())
                           && password.getText().toString().contains(users.get(i).getAccount().getPassword()))
                           {
                               Toast.makeText(login_activity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(login_activity.this, MainActivity.class);
                               startActivity(intent);
                               return;
                           }
                       }
                       Toast.makeText(login_activity.this, "sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                   }
                   else
                   {
                       Toast.makeText(login_activity.this, "Vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show();
                   }
                }
            }

            @Override
            public void onFailure(Call<List<class_user>> call, Throwable t) {
                Toast.makeText(login_activity.this, "" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
    // lấy thông tin người dùng
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Toast.makeText(this, "Sign-in Successful", Toast.LENGTH_SHORT).show();
            if (account != null) {

            }
            updateUI(true);
        } catch (ApiException e) {
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
            updateUI(false);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       //trả kết quả người dùng đăng nhập google
        if (requestCode == SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
// chuyển trang khi đăng nhập thành công
    private void updateUI(boolean signedIn) {
        if (signedIn) {
            findViewById(R.id.btnlogingoogle).setVisibility(View.GONE);
            Intent signInToMain = new Intent(login_activity.this, MainActivity.class);
            startActivity(signInToMain);
        } else {
            findViewById(R.id.btnlogingoogle).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}