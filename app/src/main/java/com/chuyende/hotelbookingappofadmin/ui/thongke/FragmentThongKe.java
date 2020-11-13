package com.chuyende.hotelbookingappofadmin.ui.thongke;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.chuyende.hotelbookingappofadmin.R;
import com.github.mikephil.charting.charts.Chart;

public class FragmentThongKe extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_thongke, container, false);
        final Spinner spLocAdminThang = root.findViewById(R.id.spLocAdminThang);
        final TextView tvDoanhThuAdmin = root.findViewById(R.id.tvDoanhThuAdmin);
        final Chart barchart_doanhthu_admin = root.findViewById(R.id.barchart_doanhthu_admin);
        return root;
    }
}