package com.chuyende.hotelbookingappofadmin.ui.nguoidung;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.chuyende.hotelbookingappofadmin.R;

public class FragmentNguoiDung extends Fragment {

    private NguoiDungViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(NguoiDungViewModel.class);
        View root = inflater.inflate(R.layout.fragment_nguoidung, container, false);
        EditText edtSearchND = root.findViewById(R.id.edtTimKiemNguoiDung);
        ImageButton btnSearchND = root.findViewById(R.id.btnTimKiemNguoiDung);
        Button btnDangXuat = root.findViewById(R.id.btnDangXuat);
        ListView lvNguoiDung = root.findViewById(R.id.lvNguoiDung);

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChiTietNguoiDung.class);
                startActivity(intent);
            }
        });
        return root;
    }
}