package com.chuyende.hotelbookingappofadmin.ui.khachsan;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.chuyende.hotelbookingappofadmin.R;
import com.chuyende.hotelbookingappofadmin.data_model.DaDat;
import com.chuyende.hotelbookingappofadmin.data_model.KhachSan;
import com.chuyende.hotelbookingappofadmin.data_model.Phong;
import com.chuyende.hotelbookingappofadmin.data_model.ThongKeKhachSan;
import com.github.mikephil.charting.charts.BarChart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ChiTietKhachSan extends AppCompatActivity {
    TextView tvTenKhachSan;
    ImageView imgAnhDaiDienKS;
    Switch swBieuDo;
    Spinner spLocThang;
    BarChart chart;
    FirebaseFirestore db;
    ArrayList<KhachSan> dataKhachSan;
    ArrayList<Phong> dataPhong;
    ArrayList<ThongKeKhachSan> dataThongKeKS;

    private static String KHACHSAN = "KhachSan";
    private static String PHONG = "Phong";
    private static String DADAT = "DaDat";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.layout_chitietkhachsan);
        db = FirebaseFirestore.getInstance();
        setControl();
        setEvent();
    }

    private void setEvent() {
        String maKhachSan = getIntent().getExtras().getString("maks");

    }

    private void setControl() {
        imgAnhDaiDienKS = findViewById(R.id.imgAnhKhachSan);
        tvTenKhachSan = findViewById(R.id.tvTenKS);
        swBieuDo = findViewById(R.id.swBieuDo);
        spLocThang = findViewById(R.id.spLocThangKS);
        chart = findViewById(R.id.barchart_khachsan);
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

    public void LayThongKeLuotDat(String maKS) {
        db.collection(PHONG).whereEqualTo("maKhachSan", maKS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                int count = 0;
                dataPhong = new ArrayList<>();
                if (task.isSuccessful()) {
                    Phong phong = new Phong();
                    QuerySnapshot querySnapshot = task.getResult();
                    for (DocumentSnapshot doc : querySnapshot) {
                        phong = doc.toObject(Phong.class);
                        dataPhong.add(phong);
                    }
                    for (Phong p : dataPhong) {
                        db.collection(DADAT).whereEqualTo("maPhong", p.getMaPhong()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    DaDat daDat = new DaDat();
                                    QuerySnapshot
                                }
                            }
                        });
                    }
                }
            }
        });
    }


}
