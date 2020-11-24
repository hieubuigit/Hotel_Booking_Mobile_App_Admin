package com.chuyende.hotelbookingappofadmin.ui.khachsan;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.chuyende.hotelbookingappofadmin.R;
import com.chuyende.hotelbookingappofadmin.ui.DangNhap;
import com.google.firebase.auth.FirebaseAuth;

public class FragmentKhachSan extends Fragment {

    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn thoát không ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Intent intoDangNhap = new Intent(getActivity(), DangNhap.class);
                        startActivity(intoDangNhap);
                    }
                });

            }
        });

        return root;
    }
}