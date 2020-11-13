package com.chuyende.hotelbookingappofadmin.ui.khachsan;

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
//        final EditText edtSearchKS = root.findViewById(R.id.edtTimKiemKhachSan);
//        final ImageButton btnSearchKS = root.findViewById(R.id.btnTimKiemKhachSan);
//        final Button btnDangXuat = root.findViewById(R.id.btnDangXuat);
//        final Spinner spLocTinhThanh = root.findViewById(R.id.spLocTinhThanh);
//        final ListView lvKhachSan = root.findViewById(R.id.lvKhachSan);
        khachSanViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
//                textView.setText(s);
            }
        });
        return root;
    }
}