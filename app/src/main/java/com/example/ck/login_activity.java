package com.example.ck;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
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

    Intent intent;

    CallbackManager callbackManager;
    LoginButton loginfacebook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        // set cho facebookapi
        callbackManager = CallbackManager.Factory.create();
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        btnregister = findViewById(R.id.btnregister);
        btnlogin = findViewById(R.id.btnlogin);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnlogingoogle = findViewById(R.id.btnlogingoogle);
        loginfacebook = findViewById(R.id.btnloginfacebook);

        //set size cho button logingoogle
        btnlogingoogle.setSize(SignInButton.SIZE_STANDARD);
        call_googleApi();

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

        // xin quyền fb lấy thông tin cá nhân
        loginfacebook.setReadPermissions(Arrays.asList("public_profile", "email"));
        set_loginfacebook();
    }

    private void set_loginfacebook()
    {
        loginfacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("JSON", response.getJSONObject().toString());
                        try {
                            MainActivity.firstname = object.getString("first_name");
                            MainActivity.email = object.getString("email");
                            MainActivity.image = object.getString("id");
                            MainActivity.profile= response.getJSONObject().getJSONObject("picture").getJSONObject("data").getString("url");
                            intent = new Intent(login_activity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                Bundle param = new Bundle();
                param.putString("fields", "name, email, gender, first_name, picture");
                request.setParameters(param);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }
    private void call_googleApi()
    {
        // call api google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
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
               // Toast.makeText(login_activity.this, "" + t, Toast.LENGTH_SHORT).show();
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
        callbackManager.onActivityResult(requestCode, resultCode, data);
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

    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }

}