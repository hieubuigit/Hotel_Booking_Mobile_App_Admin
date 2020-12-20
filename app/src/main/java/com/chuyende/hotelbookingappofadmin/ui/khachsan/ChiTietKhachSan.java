package com.chuyende.hotelbookingappofadmin.ui.khachsan;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.chuyende.hotelbookingappofadmin.R;
import com.chuyende.hotelbookingappofadmin.adapter.AdapterKhachSan;
import com.chuyende.hotelbookingappofadmin.data_model.DaDat;
import com.chuyende.hotelbookingappofadmin.data_model.KhachSan;
import com.chuyende.hotelbookingappofadmin.data_model.NguoiDung;
import com.chuyende.hotelbookingappofadmin.data_model.Phong;
import com.chuyende.hotelbookingappofadmin.data_model.ThongKeLuotDat;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class ChiTietKhachSan extends AppCompatActivity {
    TextView tvTenKhachSan;
    ImageView imgAnhDaiDienKS;
    Switch swBieuDo;
    Spinner spLocThang;
    BarChart chart;
    FirebaseFirestore db = FirebaseFirestore.getInstance();;
    KhachSan khachSan;
    Phong phong;
    DaDat daDat;
    ThongKeLuotDat thongKeLuotDat;
    ArrayList<Phong> dataPhong;
    ArrayList<DaDat> dataDaDat;
    ArrayList<ThongKeLuotDat> dataThongKeLuotDat;
    String maKhachSan;
    private static String KHACHSAN = "KhachSan";
    private static String PHONG = "Phong";
    private static String DADAT = "DaDat";
    private static String TKLD = "ThongKeLuotDat";
    private static String DATHANHTOAN = "DaThanhToan";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.layout_chitietkhachsan);
        maKhachSan = getIntent().getExtras().getString("maks");
        LayDataKS(maKhachSan);
        setControl();
        onStart();

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

    @Override
    protected void onStart() {
        super.onStart();
        LayThongKeLuotDatBieuDo(maKhachSan);
        swBieuDo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                } else {
                    LayThongKeLuotDatBieuDo(maKhachSan);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void LayDataKS (String maKS) {
        db.collection(KHACHSAN).document(maKS).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    KhachSan khachSan = new KhachSan();
                    DocumentSnapshot docSnapshot = task.getResult();
                    khachSan = docSnapshot.toObject(KhachSan.class);
                    khachSan.setMaKhachSan(docSnapshot.getId());
                    Log.d(TAG, "Lấy dữ liệu thành công");
                    tvTenKhachSan.setText(khachSan.getTenKhachSan());
                } else {
                    Log.d(TAG, "Có lỗi");
                }
            }
        });
    }

    public void LayThongKeLuotDatBieuDo(String maKS) {
        db.collection(KHACHSAN).document(maKS).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()) {
                    khachSan = value.toObject(KhachSan.class);
                    khachSan.setMaKhachSan(value.getId());
                    db.collection(PHONG).whereEqualTo("maKhachSan", khachSan.getMaKhachSan()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                            dataPhong = new ArrayList<>();
                            for (DocumentSnapshot doc : value) {
                                if (e != null) {
                                    Toast.makeText(ChiTietKhachSan.this, "Có lỗi khi load dữ liệu phòng", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, e.toString());
                                    return;
                                }
                                if (doc.exists()) {
                                    phong = doc.toObject(Phong.class);
                                    dataPhong.add(phong);
                                }
                            }
                            for (Phong p : dataPhong) {
                                db.collection(DADAT).whereEqualTo("maPhong", p.getMaPhong()).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                                        dataDaDat = new ArrayList<>();
                                        for (DocumentSnapshot doc : value) {
                                            if (e != null) {
                                                Toast.makeText(ChiTietKhachSan.this, "Có lỗi khi load dữ liệu collection đã đặt", Toast.LENGTH_SHORT).show();
                                                Log.d(TAG, e.toString());
                                                return;
                                            }
                                            if (doc.exists()) {
                                                daDat = doc.toObject(DaDat.class);
                                                daDat.setMaDat(doc.getId());
                                                dataDaDat.add(daDat);
                                            }
                                        }
                                        for (int i = 0; i < dataDaDat.size(); i++) {
                                            dataThongKeLuotDat = new ArrayList<>();
                                            if (dataDaDat.get(i).getNgayDatPhong())
                                            int thang = Integer.parseInt(dd.getNgayDatPhong().substring(3, 5));
                                            int nam = Integer.parseInt(dd.getNgayDatPhong().substring(6, 10));
                                            int tongLuotDat = 0;
                                            thongKeLuotDat.setSoLuotDatThanhCong(tongLuotDat);
                                            thongKeLuotDat.setThangDatPhong(thang + "/" + nam);
                                            ArrayList <BarEntry> entries = new ArrayList<>();
                                            entries.add(new BarEntry(thongKeLuotDat.getSoLuotDatThanhCong()), i);
                                            BarDataSet dataSet = new BarDataSet(entries, "Dữ liệu đã đặt");
                                            BarData data = new BarData(dataSet);
                                            chart.setData(data);
                                            data.setValueTextColor(Color.BLUE);
                                            dataSet.setBarShadowColor(Color.WHITE);
                                            dataSet.setValueTextSize(15);
                                            chart.setDrawBarShadow(true);
                                            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                                            chart.animateY(3000, Easing.EaseInOutBounce);
                                            chart.invalidate();

                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    public void addThongKeLuotDat(ThongKeLuotDat tkld, String key) {
        Map<String, Object> map = new HashMap<>();
        map.put("maKhachSan", tkld.getMaKhachSan());
        map.put("tenKhachSan", tkld.getTenKhachSan());
        map.put("soLuotDatThanhCong", tkld.getSoLuotDatThanhCong());
        map.put("thangDatPhong", tkld.getThangDatPhong());
        db.collection(TKLD).document(key).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "đã upload dữ liệu thống kê đã đặt phòng");
            }
        });

    }
}
