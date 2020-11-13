package com.chuyende.hotelbookingappofadmin.ui;

import android.os.Bundle;
import android.view.MenuItem;
import com.chuyende.hotelbookingappofadmin.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class Menu extends AppCompatActivity {
//    ActionBar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        BottomNavigationView navView = findViewById(R.id.nav_view);
//        toolbar = getSupportActionBar();
//        toolbar.setTitle("Khách Sạn");
        // Passing each menu ID as a set of Ids because each
        navView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_khachsan:
//                    toolbar.setTitle("Khách Sạn");
                    return true;
                case R.id.navigation_nguoidung:
//                    toolbar.setTitle("Người Dùng");
                    return true;
                case R.id.navigation_thongke:
//                    toolbar.setTitle("Thống Kê");
                    return true;
            }
            return false;
        });
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_khachsan, R.id.navigation_nguoidung, R.id.navigation_thongke)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
}