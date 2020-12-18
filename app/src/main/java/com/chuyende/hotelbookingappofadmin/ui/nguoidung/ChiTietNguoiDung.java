package com.chuyende.hotelbookingappofadmin.ui.nguoidung;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.chuyende.hotelbookingappofadmin.R;
import com.chuyende.hotelbookingappofadmin.data_model.NguoiDung;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class ChiTietNguoiDung extends AppCompatActivity {
    TextView tvTenNguoiDung, tvNgaySinh, tvGioiTinh, tvQuocTich, tvCMND, tvDiaChi, tvEmail, tvSoDienThoai;
    NguoiDung nguoiDung;
    FirebaseFirestore db;
    private final String COLLECTION_KEY = "NguoiDung";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.layout_chitietnguoidung);
        db = FirebaseFirestore.getInstance();
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
        db.collection(COLLECTION_KEY).document(maNguoiDung).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    NguoiDung nguoiDung = new NguoiDung();
                    DocumentSnapshot docSnapshot = task.getResult();
                    nguoiDung = docSnapshot.toObject(NguoiDung.class);
                    Log.d(TAG, "Lấy dữ liệu thành công");
                    tvTenNguoiDung.setText(nguoiDung.getTenNguoiDung());
                    tvNgaySinh.setText(nguoiDung.getNgaySinh());
                    tvGioiTinh.setText(nguoiDung.getGioiTinh());
                    tvQuocTich.setText(nguoiDung.getQuocTich());
                    tvCMND.setText(nguoiDung.getCmnd());
                    tvDiaChi.setText(nguoiDung.getDiaChi());
                    tvEmail.setText(nguoiDung.getEmail());
                    tvSoDienThoai.setText(nguoiDung.getSoDienThoai());
                } else {
                    Log.d(TAG, "Có lỗi");
                }
            }
        });
    }
}
