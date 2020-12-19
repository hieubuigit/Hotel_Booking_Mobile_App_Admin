package com.chuyende.hotelbookingappofadmin.ui.khachsan;

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
import com.chuyende.hotelbookingappofadmin.adapter.AdapterKhachSan;
import com.chuyende.hotelbookingappofadmin.data_model.DaDat;
import com.chuyende.hotelbookingappofadmin.data_model.KhachSan;
import com.chuyende.hotelbookingappofadmin.data_model.NguoiDung;
import com.chuyende.hotelbookingappofadmin.data_model.Phong;
import com.chuyende.hotelbookingappofadmin.data_model.ThongKeLuotDat;
import com.github.mikephil.charting.charts.BarChart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
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
    FirebaseFirestore db;
    KhachSan khachSan;
    Phong phong;
    DaDat daDat;
    ThongKeLuotDat thongKeLuotDat;
    ArrayList<KhachSan> dataKhachSan;
    ArrayList<Phong> dataPhong;
    ArrayList<ThongKeLuotDat> dataThongKeLuotDat;
    ArrayList<DaDat> dataDaDat;
    int tongLuotDat = 0;
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
        db = FirebaseFirestore.getInstance();
        setControl();
        setEvent();
    }

    private void setEvent() {
        String maKhachSan = getIntent().getExtras().getString("maks");
        LayDataKS(maKhachSan);
        LayThongKeLuotDat(maKhachSan);
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

    public void LayThongKeLuotDat(String maKS) {
        db.collection(PHONG).whereEqualTo("maKhachSan", maKS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                dataPhong = new ArrayList<>();
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    for (DocumentSnapshot doc : querySnapshot) {
                        phong = doc.toObject(Phong.class);
                        phong.setMaPhong(doc.getId());
                        dataPhong.add(phong);
                    }
                    for (Phong p : dataPhong) {
                        db.collection(DADAT).whereEqualTo("maPhong", p.getMaPhong()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                dataDaDat = new ArrayList<>();
                                if (task.isSuccessful()) {
                                    QuerySnapshot querySnapshot1 = task.getResult();
                                    for (DocumentSnapshot doc1 : querySnapshot1) {
                                        daDat = doc1.toObject(DaDat.class);
                                        daDat.setMaDat(doc1.getId());
                                        dataDaDat.add(daDat);
                                    }
                                    db.collection(KHACHSAN).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            dataKhachSan = new ArrayList<>();
                                            if (task.isSuccessful()) {
                                                QuerySnapshot querySnapshot = task.getResult();
                                                for (DocumentSnapshot doc : querySnapshot) {
                                                    khachSan = doc.toObject(KhachSan.class);
                                                    khachSan.setMaKhachSan(doc.getId());
                                                    dataKhachSan.add(khachSan);
                                                }
                                                dataThongKeLuotDat = new ArrayList<>();
                                                for (DaDat dd : dataDaDat) {
                                                    int i;
                                                    int thang = Integer.parseInt(dd.getNgayDatPhong().substring(3, 4));
                                                    int nam = Integer.parseInt(dd.getNgayDatPhong().substring(6, 10));
                                                    for (i = 1; i <= 12; i++) {
                                                        if (thang == i) {
                                                            for (KhachSan ks : dataKhachSan) {
                                                                if (ks.getMaKhachSan().equalsIgnoreCase(p.getMaKhachSan())) {
                                                                    tongLuotDat += p.getSoLuotDat();
                                                                    thongKeLuotDat.setMaKhachSan(ks.getMaKhachSan());
                                                                    thongKeLuotDat.setTenKhachSan(ks.getTenKhachSan());
                                                                    thongKeLuotDat.setSoLuotDatThanhCong(tongLuotDat);
                                                                    addThongKeLuotDat(thongKeLuotDat);
                                                                    if (thang > 10) {
                                                                        thongKeLuotDat.setThangDatPhong(thang + "/ " + nam);
                                                                    }
                                                                    else {
                                                                        thongKeLuotDat.setThangDatPhong("0" + thang + "/ " + nam);
                                                                    }


                                                                }
                                                            }
                                                        }
                                                    }
//                                                    for (KhachSan ks : dataKhachSan) {
//                                                        if (ks.getMaKhachSan().equalsIgnoreCase(maKS)) {
//
//                                                            for (i = 1; i <= 12; i++) {
//                                                                if (thang == i) {
//                                                                    tongLuotDat += p.getSoLuotDat();
//                                                                    thangDat = i + "/ " + nam;
//                                                                    thongKeKS.setThangDatPhong(thangDat);
//                                                                    thongKeKS.setMaKhachSan(ks.getMaKhachSan());
//                                                                    thongKeKS.setTenKhachSan(ks.getTenKhachSan());
//                                                                    thongKeKS.setSoLuotDatThanhCong(tongLuotDat);
//                                                                    addThongKeKS(thongKeKS);
//                                                                }
//                                                            }
//
//                                                        }
//                                                    }
                                                }
                                                Log.d(TAG, "đã thêm vào collection");
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    public void addThongKeLuotDat(ThongKeLuotDat tkld) {
        Map<String, Object> map = new HashMap<>();
        map.put("maKhachSan", tkld.getMaKhachSan());
        map.put("tenKhachSan", tkld.getMaKhachSan());
        map.put("soLuotDatThanhCong", tkld.getSoLuotDatThanhCong());
        map.put("thangDatPhong", tkld.getThangDatPhong());
        db.collection(TKLD).add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "dữ liệu đã được thêm");
            }
        });
    }

}
