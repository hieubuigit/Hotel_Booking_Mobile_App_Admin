package com.chuyende.hotelbookingappofadmin.ui.khachsan;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.chuyende.hotelbookingappofadmin.R;
import com.chuyende.hotelbookingappofadmin.data_model.DaThanhToan;
import com.chuyende.hotelbookingappofadmin.data_model.KhachSan;
import com.chuyende.hotelbookingappofadmin.data_model.Phong;
import com.chuyende.hotelbookingappofadmin.firebase.FireStore_KhachSan;
import com.chuyende.hotelbookingappofadmin.firebase.Firestore_DaThanhToan;
import com.chuyende.hotelbookingappofadmin.firebase.Firestore_Phong;
import com.chuyende.hotelbookingappofadmin.interfaces.CallBackKhachSanByID;
import com.chuyende.hotelbookingappofadmin.interfaces.CallBackListPhongByKSID;
import com.chuyende.hotelbookingappofadmin.interfaces.CallBackListThanhToan;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class ChiTietKhachSan extends AppCompatActivity {
    TextView tvTenKhachSan;
    ImageView imgAnhDaiDienKS;
    Switch swChart;
    Spinner spLocThang;
    BarChart chart;

    FireStore_KhachSan dbKhachSan = new FireStore_KhachSan();
    Firestore_Phong dbPhong = new Firestore_Phong();
    Firestore_DaThanhToan dbDaThanhToan = new Firestore_DaThanhToan();
    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();

    ArrayList<String> thangDat;
    String maKhachSan;
    ArrayAdapter adapterThang;

    private static String PATH_PHONG = "/media/khachSan/";
    private static String DEFAULT = "Month";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.layout_chitietkhachsan);
        maKhachSan = getIntent().getExtras().getString("maks");
        LayDataKS(maKhachSan);
        setControl();
        LayBieuDoDaDatPhong(maKhachSan);
        swChart.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    LayBieuDoDoanhThu(maKhachSan);
                } else {
                    LayBieuDoDaDatPhong(maKhachSan);
                }
            }
        });

    }

    private void setControl() {
        imgAnhDaiDienKS = findViewById(R.id.imgAnhKhachSan);
        tvTenKhachSan = findViewById(R.id.tvTenKS);
        swChart = findViewById(R.id.swBieuDo);
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


    public void LayDataKS(String maKS) {
        try {
            dbKhachSan.getKhachSanByID(maKS, new CallBackKhachSanByID() {
                @Override
                public void onDataCallBackKhachSanbyID(KhachSan khachSan) {
                    tvTenKhachSan.setText(khachSan.getTenKhachSan());
                    String url = PATH_PHONG + maKS + "/" + maKS + ".jpg";
                    mStorageRef.child(url).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Glide.with(ChiTietKhachSan.this).load(uri).into(imgAnhDaiDienKS);
                        }
                    });
                }
            });
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }


    public void LayBieuDoDaDatPhong(String maKS) {
        try {
            dbKhachSan.getKhachSanByID(maKS, new CallBackKhachSanByID() {
                @Override
                public void onDataCallBackKhachSanbyID(KhachSan khachSan) {
                    thangDat = new ArrayList<>();
                    dbPhong.getAllPhongByMaKS(maKS, new CallBackListPhongByKSID() {
                        @Override
                        public void onDataGetListPhongByKSID(ArrayList<Phong> listPhong) {
                            for (Phong p : listPhong) {
                                try {
                                    dbDaThanhToan.getAllDaThanhToan(new CallBackListThanhToan() {
                                        @Override
                                        public void onDataCallBackDaThanhToan(ArrayList<DaThanhToan> listDaThanhToan) {
                                            for (DaThanhToan dtt : listDaThanhToan) {
                                                if (dtt.getMaPhong().equalsIgnoreCase(p.getMaPhong()) && dtt.getTrangThaiHoanTatThanhToan().equals("true")) {
                                                    thangDat.add(dtt.getNgayThanhToan().substring(3, 10));
                                                }
                                            }
                                            Log.d(TAG, "total item: " + listDaThanhToan.size());
                                            ArrayList<String> listThang = new ArrayList<>();
                                            listThang.add(DEFAULT);
                                            for (String str : thangDat) {
                                                if (!listThang.contains(str)) {
                                                    listThang.add(str);
                                                }
                                            }
                                            adapterThang = new ArrayAdapter(ChiTietKhachSan.this, R.layout.support_simple_spinner_dropdown_item, listThang);
                                            spLocThang.setAdapter(adapterThang);
                                            adapterThang.notifyDataSetChanged();
                                            spLocThang.setSelection(getIndex(spLocThang, ""));
                                            spLocThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                    ArrayList<BarEntry> entries = new ArrayList<>();
                                                    String thangDatPhong = spLocThang.getSelectedItem().toString();

                                                    Log.d(TAG, "tháng đc chọn trong spinner: " + thangDatPhong);

                                                    int tongLuotDat = 0;
                                                    for (DaThanhToan dtt : listDaThanhToan) {

                                                        if (dtt.getMaPhong().contains(khachSan.getMaKhachSan()) && dtt.getNgayThanhToan().substring(3, 10).equals(thangDatPhong) && dtt.getTrangThaiHoanTatThanhToan().equals("true")) {
                                                            tongLuotDat++;
                                                        }
                                                    }
                                                    Log.d(TAG, "tổng lượt đặt: " + tongLuotDat);
                                                    entries.add(new BarEntry(0, tongLuotDat));
                                                    BarDataSet dataSet = new BarDataSet(entries, "Dữ liệu Đã đặt");
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

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {

                                                }
                                            });
                                        }
                                    });
                                } catch (Exception e) {
                                    Log.d(TAG, e.toString());
                                }
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    public void LayBieuDoDoanhThu(String maKS) {
        try {
            dbKhachSan.getKhachSanByID(maKS, new CallBackKhachSanByID() {
                @Override
                public void onDataCallBackKhachSanbyID(KhachSan khachSan) {
                    thangDat = new ArrayList<>();
                    dbPhong.getAllPhongByMaKS(maKS, new CallBackListPhongByKSID() {
                        @Override
                        public void onDataGetListPhongByKSID(ArrayList<Phong> listPhong) {
                            for (Phong p : listPhong) {
                                try {
                                    dbDaThanhToan.getAllDaThanhToan(new CallBackListThanhToan() {
                                        @Override
                                        public void onDataCallBackDaThanhToan(ArrayList<DaThanhToan> listDaThanhToan) {
                                            for (DaThanhToan dtt : listDaThanhToan) {
                                                if (dtt.getMaPhong().equalsIgnoreCase(p.getMaPhong()) && dtt.getTrangThaiHoanTatThanhToan().equals("true")) {
                                                    thangDat.add(dtt.getNgayThanhToan().substring(3, 10));
                                                }
                                            }
                                            Log.d(TAG, "total item: " + listDaThanhToan.size());
                                            ArrayList<String> listThang = new ArrayList<>();
                                            listThang.add(DEFAULT);
                                            for (String str : thangDat) {
                                                if (!listThang.contains(str)) {
                                                    listThang.add(str);
                                                }
                                            }
                                            adapterThang = new ArrayAdapter(ChiTietKhachSan.this, R.layout.support_simple_spinner_dropdown_item, listThang);
                                            spLocThang.setAdapter(adapterThang);
                                            spLocThang.setSelection(getIndex(spLocThang, ""));
                                            spLocThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                    ArrayList<BarEntry> entries = new ArrayList<>();
                                                    String thangDatPhong = spLocThang.getSelectedItem().toString();

                                                    Log.d(TAG, "tháng đc chọn trong spinner: " + thangDatPhong);

                                                    int doanhThu = 0;
                                                    for (DaThanhToan dtt : listDaThanhToan) {

                                                        if (dtt.getMaPhong().contains(khachSan.getMaKhachSan()) && dtt.getNgayThanhToan().substring(3, 10).equals(thangDatPhong) && dtt.getTrangThaiHoanTatThanhToan().equals("true")) {
                                                            doanhThu += dtt.getTongThanhToan();
                                                        }
                                                    }
                                                    Log.d(TAG, "doanh thu: " + doanhThu);
                                                    entries.add(new BarEntry(0, doanhThu));
                                                    BarDataSet dataSet = new BarDataSet(entries, "Dữ liệu Doanh thu");
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

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {

                                                }
                                            });
                                        }
                                    });
                                } catch (Exception e) {
                                    Log.d(TAG, e.toString());
                                }
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }

        return 0;
    }


}
