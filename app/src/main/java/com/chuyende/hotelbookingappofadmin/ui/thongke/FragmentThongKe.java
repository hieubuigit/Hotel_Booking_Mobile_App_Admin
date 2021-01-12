package com.chuyende.hotelbookingappofadmin.ui.thongke;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.chuyende.hotelbookingappofadmin.R;
import com.chuyende.hotelbookingappofadmin.data_model.DaThanhToan;
import com.chuyende.hotelbookingappofadmin.firebase.Firestore_DaThanhToan;
import com.chuyende.hotelbookingappofadmin.interfaces.CallBackListThang;
import com.chuyende.hotelbookingappofadmin.interfaces.CallBackListThanhToan;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class FragmentThongKe extends Fragment {
    private ThongKeViewModel notificationsViewModel;

    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    public Firestore_DaThanhToan dbThanhToan = new Firestore_DaThanhToan();
    public static final String ALL = "Default";

    Spinner spLocAdminThang;
    TextView tvDoanhThuAdmin;
    BarChart chartDoanhThuAdmin;

    Locale localeVN = new Locale("vi", "VN");
    Currency currencyVN = Currency.getInstance(localeVN);
    NumberFormat currencyVNFormater = NumberFormat.getInstance(localeVN);
    String symbol = currencyVN.getSymbol(localeVN);

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(ThongKeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_thongke, container, false);
        spLocAdminThang = root.findViewById(R.id.spLocAdminThang);
        tvDoanhThuAdmin = root.findViewById(R.id.tvDoanhThuAdmin);
        chartDoanhThuAdmin = root.findViewById(R.id.barchart_doanhthu_admin);

        dbThanhToan.getAllDaThanhToan(new CallBackListThanhToan() {
            @Override
            public void onDataCallBackDaThanhToan(ArrayList<DaThanhToan> listDaThanhToan) {
                try {
                    dbThanhToan.getAllThang(new CallBackListThang() {
                        @Override
                        public void onDataGetListThang(ArrayList<String> listThang) {
                            ArrayList<String> listThangFilter = new ArrayList<>();
                            listThangFilter.add(ALL);
                            for (String thang : listThang) {
                                if (!listThangFilter.contains(thang)) {
                                    listThangFilter.add(thang);
                                }
                            }
                            ArrayAdapter<String> adapterTinhThanh = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, listThangFilter);
                            spLocAdminThang.setAdapter(adapterTinhThanh);
                            adapterTinhThanh.notifyDataSetChanged();
                            spLocAdminThang.setSelection(getIndex(spLocAdminThang, ""));
                            spLocAdminThang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    ArrayList<BarEntry> entries = new ArrayList<>();
                                    String thangDatPhong = spLocAdminThang.getSelectedItem().toString();
                                    Log.d(TAG, "tháng đc chọn trong spinner: " + thangDatPhong);

                                    int doanhThu = 0;
                                    for (DaThanhToan dtt : listDaThanhToan) {
                                        if (dtt.getNgayThanhToan().substring(3, 10).equals(thangDatPhong) && dtt.getTrangThaiHoanTatThanhToan().equals("true")) {
                                            doanhThu += dtt.getTongThanhToan();

                                        }
                                    }
                                    Log.d(TAG, "tổng doanh thu: " + doanhThu);
                                    tvDoanhThuAdmin.setText(currencyVNFormater.format(doanhThu) + " " + symbol);
                                    entries.add(new BarEntry(0, doanhThu));
                                    BarDataSet dataSet = new BarDataSet(entries, "Dữ liệu Đã đặt");
                                    BarData data = new BarData(dataSet);
                                    chartDoanhThuAdmin.setData(data);
                                    data.setValueTextColor(Color.BLUE);
                                    dataSet.setBarShadowColor(Color.WHITE);
                                    dataSet.setValueTextSize(15);
                                    chartDoanhThuAdmin.setDrawBarShadow(true);
                                    dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                                    chartDoanhThuAdmin.animateY(3000, Easing.EaseInOutBounce);
                                    chartDoanhThuAdmin.invalidate();

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
        });

        return root;
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