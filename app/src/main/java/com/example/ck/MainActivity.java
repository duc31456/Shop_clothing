package com.example.ck;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ck.fragment.fragment_dobongda;
import com.example.ck.fragment.fragment_dotapgym;
import com.example.ck.fragment.fragment_dotreem;
import com.example.ck.fragment.fragment_home;
import com.example.ck.fragment.fragment_lichsumuahang;
import com.example.ck.fragment.fragment_map;
import com.example.ck.request_api.CallApiUser;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    // load thông tin profile fb
    public static String firstname = "",email = "", image = "";

    private static final int NAV_HOME = 0;
    private static final int NAV_DOBONGDA = 1;
    private static final int NAV_DOTAPGYM = 2;
    private static final int NAV_DOTREEM= 3;
    private static final int NAV_LICHSUMUAHANG = 4;
    private static final int NAV_MAP = 5;
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
        //đăng nhập thành công
        if (!firstname.isEmpty()) {
            change_visible();
            tenkhachhang.setText("Xin chào, "+firstname);
            //Toast.makeText(this, ""+image, Toast.LENGTH_SHORT).show();
            if (image.isEmpty())
            {
                imagekhachhang.setImageResource(R.drawable.programmer);
            }else {
                try {
                    byte[] decodedString = Base64.decode(image.substring(22), Base64.DEFAULT);
                   // Glide.with(navigationView).load(decodedString).into(imagekhachhang);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    
                    imagekhachhang.setImageBitmap(decodedByte);
                }catch (Exception e)
                {
                   // Log.d("ERRORLOGIN", e+"");
                }
            }
        }
    }

    private void change_visible()
    {
        MenuItem item_dangnhap =  navigationView.getMenu().findItem(R.id.nav_dangnhap);
        MenuItem item_dangxuat =  navigationView.getMenu().findItem(R.id.nav_dangxuat);
        MenuItem item_lichsumuahang =  navigationView.getMenu().findItem(R.id.nav_lichsumuahang);

        item_dangnhap.setVisible(false);
        item_dangxuat.setVisible(true);
        item_lichsumuahang.setVisible(true);
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
        else if(id == R.id.nav_dobongda)
        {
            if(current_fragment != NAV_DOBONGDA)
            {
                replace_fragment(new fragment_dobongda());
                current_fragment = NAV_DOBONGDA;
            }
        }
        else if(id == R.id.nav_dotapgym)
        {
            if(current_fragment != NAV_DOTAPGYM)
            {
                replace_fragment(new fragment_dotapgym());
                current_fragment = NAV_DOTAPGYM;
            }
        }
        else if(id == R.id.nav_dotreem)
        {
            if(current_fragment != NAV_DOTREEM)
            {
                replace_fragment(new fragment_dotreem());
                current_fragment = NAV_DOTREEM;
            }
        }else if(id == R.id.nav_lichsumuahang)
        {
            if(current_fragment != NAV_LICHSUMUAHANG)
            {
                replace_fragment(new fragment_lichsumuahang());
                current_fragment = NAV_LICHSUMUAHANG;
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
        else if(id == R.id.nav_dotreem){

        }else if(id == R.id.nav_dangnhap)
        {
            Intent intent = new Intent(MainActivity.this, login_activity.class);
            startActivity(intent);

        }else if(id == R.id.nav_dangxuat)
        {
            firstname ="";
            image = "";
            email ="";
            imagekhachhang.setImageResource(R.drawable.programmer);
            Intent intent = new Intent(MainActivity.this, login_activity.class);
            startActivity(intent);
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