package com.example.ck;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ck.fragment.fragment_donam;
import com.example.ck.fragment.fragment_donu;
import com.example.ck.fragment.fragment_home;
import com.example.ck.fragment.fragment_map;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    GoogleSignInClient googleSignInClient;
    GoogleSignInAccount account;

    // load thông tin profile fb
    public static String firstname = "",email = "", image = "", profile ="";

    private static final int NAV_HOME = 0;
    private static final int NAV_DONU = 1;
    private static final int NAV_DONAM = 2;
    private static final int NAV_MAP = 3;
    TextView tenkhachhang;
    CircleImageView imagekhachhang;

    private int current_fragment = NAV_HOME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toobar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this,
                drawerLayout, toolbar, R.string.nav_stringopen, R.string.nav_stringclose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
        tenkhachhang = headerView.findViewById(R.id.tenkhachhang);
        imagekhachhang = headerView.findViewById(R.id.imagekhachhang);
        imagekhachhang.setImageResource(R.drawable.programmer);
        navigationView.setNavigationItemSelectedListener(this);

        //lúc chạy thì set fragment sẽ là home
        replace_fragment(new fragment_home());
        //check clickable item home trong navigation
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);

        call_googleApi();
        //nếu đăng nhập fb
        if (!email.isEmpty() && !firstname.isEmpty() && !image.isEmpty()) {
            Toast.makeText(this, email + firstname, Toast.LENGTH_SHORT).show();
            change_visible();
            tenkhachhang.setText("Xin chào, "+firstname);
            try{
                Glide.with(navigationView).load(profile).into(imagekhachhang);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // hàm gọi api google lấy image và tên người đăng nhập google
    public void call_googleApi()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        //nếu đã đăng nhập thì hiện đăng xuất và thông tin tài khoản
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        account = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
        if (account != null) {
            change_visible();
            tenkhachhang.setText("Xin chào, "+account.getDisplayName());
            Glide.with(navigationView).load(account.getPhotoUrl()).into(imagekhachhang);
        }
    }
    private void change_visible()
    {
        MenuItem item_thongtin =  navigationView.getMenu().findItem(R.id.nav_thongtintaikhoan);
        MenuItem item_dangnhap =  navigationView.getMenu().findItem(R.id.nav_dangnhap);
        MenuItem item_dangxuat =  navigationView.getMenu().findItem(R.id.nav_dangxuat);

        item_dangnhap.setVisible(false);
        item_dangxuat.setVisible(true);
        item_thongtin.setVisible(true);
    }
    // đăng xuất google
    private void signOutGoogle() {
        googleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                      Intent intent = new Intent(MainActivity.this, login_activity.class);
                      startActivity(intent);
                        Toast.makeText(MainActivity.this, "Đăng xuất mail thành công!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //đăng xuất google
    private void signOutFacebook()
    {
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(MainActivity.this, login_activity.class);
        startActivity(intent);
        Toast.makeText(MainActivity.this, "Đăng xuất facebook thành công!", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.nav_home)
        {
            if(current_fragment != NAV_HOME)
            {
                replace_fragment(new fragment_home());
                current_fragment = NAV_HOME;
            }
        }
        else if(id == R.id.nav_donam)
        {
            if(current_fragment != NAV_DONAM)
            {
                replace_fragment(new fragment_donam());
                current_fragment = NAV_DONAM;
            }
        }
        else if(id == R.id.nav_donu)
        {
            if(current_fragment != NAV_DONU)
            {
                replace_fragment(new fragment_donu());
                current_fragment = NAV_DONU;
            }
        }
        else if(id == R.id.nav_map)
        {
            if(current_fragment != NAV_MAP)
            {

               replace_fragment(new fragment_map());
                current_fragment = NAV_MAP;
            }
        }
        else if(id == R.id.nav_dodoi){

        }else if(id == R.id.nav_dangnhap)
        {
            Intent intent = new Intent(MainActivity.this, login_activity.class);
            startActivity(intent);

        }else if(id == R.id.nav_dangxuat)
        {
            if(account != null)
            {
                signOutGoogle();
            }
            if(!email.isEmpty() && !firstname.isEmpty() && !image.isEmpty())
            {
                signOutFacebook();
                image = "";
                firstname ="";
                email ="";
                imagekhachhang.setImageResource(R.drawable.programmer);
            }
        }else if(id == R.id.nav_thongtintaikhoan)
        {
           // signOut();
        }

        //đóng navigation view lại
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void replace_fragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

    }
    @Override
    public void onBackPressed() {
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.item_cart)
        {
            Intent intent = new Intent(MainActivity.this, cart_activity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}