package com.chuyende.hotelbookingappofadmin.ui.khachsan;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.chuyende.hotelbookingappofadmin.R;
import com.github.mikephil.charting.charts.BarChart;

public class ChiTietKhachSan extends AppCompatActivity {
    TextView tvTenKhachSan;
    ImageView imgAnhDaiDienKS;
    Switch swBieuDo;
    Spinner spLocThang;
    BarChart
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.layout_chitietkhachsan);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return true;
    }
}
