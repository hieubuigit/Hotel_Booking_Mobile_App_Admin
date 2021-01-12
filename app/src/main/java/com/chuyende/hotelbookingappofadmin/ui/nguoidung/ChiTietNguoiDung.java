package com.chuyende.hotelbookingappofadmin.ui.nguoidung;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.chuyende.hotelbookingappofadmin.R;
import com.chuyende.hotelbookingappofadmin.data_model.NguoiDung;
import com.chuyende.hotelbookingappofadmin.firebase.FireStore_NguoiDung;
import com.chuyende.hotelbookingappofadmin.interfaces.CallBackNguoiDungByID;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class ChiTietNguoiDung extends AppCompatActivity {
    TextView tvTenNguoiDung, tvNgaySinh, tvGioiTinh, tvQuocTich, tvCMND, tvDiaChi, tvEmail, tvSoDienThoai;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FireStore_NguoiDung dbNguoiDung = new FireStore_NguoiDung();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.layout_chitietnguoidung);
        setControl();
        setEvent();
    }

    private void setEvent() {
        String maNguoiDung = getIntent().getExtras().getString("mand");
        getNguoiDung(maNguoiDung);
    }

    private void setControl() {
        tvTenNguoiDung = findViewById(R.id.tvHoTen);
        tvNgaySinh = findViewById(R.id.tvNgaySinh);
        tvGioiTinh = findViewById(R.id.tvGioiTinh);
        tvQuocTich = findViewById(R.id.tvQuocTich);
        tvCMND = findViewById(R.id.tvCMND);
        tvDiaChi = findViewById(R.id.tvDiaChi);
        tvEmail = findViewById(R.id.tvEmail);
        tvSoDienThoai = findViewById(R.id.tvSoDT);
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

    public void getNguoiDung(String maNguoiDung) {
        try {
            dbNguoiDung.getNguoiDungByID(maNguoiDung, new CallBackNguoiDungByID() {
                @Override
                public void onDataCallBackNguoiDungByID(NguoiDung nguoiDung) {
                    tvTenNguoiDung.setText(nguoiDung.getTenNguoiDung());
                    tvNgaySinh.setText(nguoiDung.getNgaySinh());
                    tvGioiTinh.setText(nguoiDung.getGioiTinh());
                    tvQuocTich.setText(nguoiDung.getQuocTich());
                    tvCMND.setText(nguoiDung.getCmnd());
                    tvDiaChi.setText(nguoiDung.getDiaChi());
                    tvEmail.setText(nguoiDung.getEmail());
                    tvSoDienThoai.setText(nguoiDung.getSoDienThoai());
                }
            });
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }
}
