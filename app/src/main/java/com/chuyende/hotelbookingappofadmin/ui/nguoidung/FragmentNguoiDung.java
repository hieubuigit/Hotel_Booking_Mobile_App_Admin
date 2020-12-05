package com.chuyende.hotelbookingappofadmin.ui.nguoidung;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class FragmentNguoiDung extends Fragment {
    private final String COLLECTION_KEY = "NguoiDung";
    EditText edtSearchND;
    ImageButton btnSearchND;
    Button btnDangXuat;
    ListView lvNguoiDung;
    FireStore_NguoiDung fbNguoiDung = new FireStore_NguoiDung();
    CustomAdapterNguoiDung adapterNguoiDung;
    FirebaseFirestore db;
    NguoiDung nguoiDung;
    private NguoiDungViewModel dashboardViewModel;

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_user:

                return true;
            case R.id.lock_user:

                return true;
            case R.id.unlock_user:

                return true;
        }
        return super.onContextItemSelected(item);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(NguoiDungViewModel.class);
        View root = inflater.inflate(R.layout.fragment_nguoidung, container, false);

        edtSearchND = root.findViewById(R.id.edtTimKiemNguoiDung);
        btnSearchND = root.findViewById(R.id.btnTimKiemNguoiDung);
        btnDangXuat = root.findViewById(R.id.btnDangXuat);
        lvNguoiDung = root.findViewById(R.id.lvNguoiDung);
        db = FirebaseFirestore.getInstance();
        registerForContextMenu(lvNguoiDung);
        db.collection(COLLECTION_KEY).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<NguoiDung> dataNguoiDung = new ArrayList<>();
                    QuerySnapshot querySnapshot = task.getResult();
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        nguoiDung = documentSnapshot.toObject(NguoiDung.class);
                        dataNguoiDung.add(nguoiDung);
                    }
                    adapterNguoiDung = new CustomAdapterNguoiDung(getActivity(), R.layout.custom_item_nguoidung, dataNguoiDung);
                    lvNguoiDung.setAdapter(adapterNguoiDung);
                    Log.d(TAG, "Lấy dữ liệu thành công");
                } else {
                    Log.d(TAG, "Có lỗi");
                }
            }
        });



        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChiTietNguoiDung.class);
                startActivity(intent);
            }
        });

        lvNguoiDung.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ChiTietNguoiDung.class);
                Bundle bundle = new Bundle();
                bundle.putString("mand", nguoiDung.getMaNguoiDung());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        return root;
    }

    public boolean checkTrangThaiNguoiDung(String )

}