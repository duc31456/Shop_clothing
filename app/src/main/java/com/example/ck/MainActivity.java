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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ck.fragment.fragment_donam;
import com.example.ck.fragment.fragment_donu;
import com.example.ck.fragment.fragment_home;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;

    private static final int NAV_HOME = 0;
    private static final int NAV_FAVORITE = 1;
    private static final int NAV_HISTORY = 2;

    private int current_fragment = NAV_HOME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toobar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this,
                drawerLayout, toolbar, R.string.nav_stringopen, R.string.nav_stringclose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //lúc chạy thì set fragment sẽ là home
        replace_fragment(new fragment_home());
        //check clickable item home trong navigation
        navigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
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
            if(current_fragment != NAV_FAVORITE)
            {
                replace_fragment(new fragment_donam());
                current_fragment = NAV_FAVORITE;
            }
        }
        else if(id == R.id.nav_donu)
        {
            if(current_fragment != NAV_HISTORY)
            {
                replace_fragment(new fragment_donu());
                current_fragment = NAV_HISTORY;
            }
        }
        else if(id == R.id.nav_dodoi){

        }else if(id == R.id.nav_dangnhap)
        {
            Intent intent = new Intent(MainActivity.this, login_activity.class);
            startActivity(intent);

        }else if(id == R.id.nav_dangky)
        {
            Intent intent = new Intent(MainActivity.this, register_activity.class);
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
}