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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.chuyende.hotelbookingappofadmin.R;
import com.chuyende.hotelbookingappofadmin.adapter.CustomAdapterNguoiDung;
import com.chuyende.hotelbookingappofadmin.data_model.NguoiDung;
import com.chuyende.hotelbookingappofadmin.firebase.FireStore_NguoiDung;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FragmentNguoiDung extends Fragment {
    EditText edtSearchND;
    ImageButton btnSearchND;
    Button btnDangXuat;
    ListView lvNguoiDung;
    FireStore_NguoiDung fbNguoiDung;
    CustomAdapterNguoiDung adapterNguoiDung;
    ArrayList<NguoiDung> dataNguoiDung = new ArrayList<>();
    FirebaseFirestore db;

    private NguoiDungViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(NguoiDungViewModel.class);
        View root = inflater.inflate(R.layout.fragment_nguoidung, container, false);

        edtSearchND = root.findViewById(R.id.edtTimKiemNguoiDung);
        btnSearchND = root.findViewById(R.id.btnTimKiemNguoiDung);
        btnDangXuat = root.findViewById(R.id.btnDangXuat);
        lvNguoiDung = root.findViewById(R.id.lvNguoiDung);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fbNguoiDung = new FireStore_NguoiDung();
        dataNguoiDung = fbNguoiDung.getListAllNguoiDung();
        adapterNguoiDung = new CustomAdapterNguoiDung(getActivity(), R.layout.custom_item_nguoidung, dataNguoiDung);
        adapterNguoiDung.notifyDataSetChanged();
        lvNguoiDung.setAdapter(adapterNguoiDung);

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChiTietNguoiDung.class);
                startActivity(intent);
            }
        });

    }
}