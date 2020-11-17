package com.chuyende.hotelbookingappofadmin.ui.khachsan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.chuyende.hotelbookingappofadmin.R;

public class FragmentKhachSan extends Fragment {


    private KhachSanViewModel khachSanViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        khachSanViewModel =
                new ViewModelProvider(this).get(KhachSanViewModel.class);
        View root = inflater.inflate(R.layout.fragment_khachsan, container, false);
        EditText edtSearchKS = root.findViewById(R.id.edtTimKiemKhachSan);
        ImageButton btnSearchKS = root.findViewById(R.id.btnTimKiemKhachSan);
        Button btnDangXuat = root.findViewById(R.id.btnDangXuat);
        Spinner spLocTinhThanh = root.findViewById(R.id.spLocTinhThanh);
        ListView lvKhachSan = root.findViewById(R.id.lvKhachSan);

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChiTietKhachSan.class);
                startActivity(intent);
            }
        });

        return root;
    }
}