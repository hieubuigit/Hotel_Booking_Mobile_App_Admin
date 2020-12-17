package com.chuyende.hotelbookingappofadmin.ui.khachsan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofadmin.R;
import com.chuyende.hotelbookingappofadmin.adapter.AdapterKhachSan;
import com.chuyende.hotelbookingappofadmin.adapter.AdapterNguoiDung;
import com.chuyende.hotelbookingappofadmin.data_model.KhachSan;
import com.chuyende.hotelbookingappofadmin.data_model.NguoiDung;
import com.chuyende.hotelbookingappofadmin.data_model.TaiKhoanKhachSan;
import com.chuyende.hotelbookingappofadmin.data_model.TaiKhoanNguoiDung;
import com.chuyende.hotelbookingappofadmin.ui.nguoidung.ChiTietNguoiDung;
import com.chuyende.hotelbookingappofadmin.ui.nguoidung.NguoiDungViewModel;
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

public class FragmentKhachSan extends Fragment {
    private final String COLLECTION_KEY_1 = "KhachSan";
    private final String COLLECTION_KEY_2 = "TaiKhoanKhachSan";
    private AdapterKhachSan.ItemClickListener listener;
    SearchView svKhachSan;
    Button btnDangXuat;
    RecyclerView rvKhachSan;
    AdapterKhachSan adapterKhachSan;
    FirebaseFirestore db;
    KhachSan khachSan;
    TaiKhoanKhachSan taiKhoanKhachSan;
    ArrayList<KhachSan> dataKhachSan;
    ArrayList<TaiKhoanKhachSan> dataTKKhachSan;
    private KhachSanViewModel dashboardViewModel;

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_menu_khachsan, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.lock_user:
                db.collection(COLLECTION_KEY_2).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            dataTKKhachSan = new ArrayList<>();
                            QuerySnapshot querySnapshot = task.getResult();
                            for (DocumentSnapshot documentSnapshot : querySnapshot) {
                                taiKhoanKhachSan = documentSnapshot.toObject(TaiKhoanKhachSan.class);
                                taiKhoanKhachSan.setIdTaiKhoanKhachSan(documentSnapshot.getId());
                                dataTKKhachSan.add(taiKhoanKhachSan);
                            }
                            Log.d(TAG, "Lấy dữ liệu thành công");
                            db.collection(COLLECTION_KEY_1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        dataKhachSan = new ArrayList<>();
                                        QuerySnapshot querySnapshot = task.getResult();
                                        for (DocumentSnapshot documentSnapshot : querySnapshot) {
                                            khachSan = documentSnapshot.toObject(KhachSan.class);
                                            khachSan.setMaKhachSan(documentSnapshot.getId());
                                            dataKhachSan.add(khachSan);
                                        }
                                        Log.d(TAG, "Lấy dữ liệu thành công");
                                        for (KhachSan ks : dataKhachSan) {
                                            for (TaiKhoanKhachSan tkks : dataTKKhachSan) {
                                                if (tkks.getTenTaiKhoanKhachSan().equalsIgnoreCase(ks.getTenTaiKhoanKhachSan())) {
                                                    if (tkks.getTrangThaiTaiKhoan().equals("true")) {
                                                        tkks.setTrangThaiTaiKhoan("false");
                                                        UpdateTKKhachSan(tkks);
                                                        Toast.makeText(getActivity(), "Okie, Tài khoản đã được khóa", Toast.LENGTH_SHORT).show();
                                                        break;
                                                    } else if (tkks.getTrangThaiTaiKhoan().equals("false")) {
                                                        Toast.makeText(getActivity(), "Tài khoản đã được khóa trước đây", Toast.LENGTH_SHORT).show();
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        Log.d(TAG, "Có lỗi");
                                    }
                                }

                            });
                        } else {
                            Log.d(TAG, "Có lỗi");
                        }
                    }
                });
                return true;
            case R.id.unlock_user:
                db.collection(COLLECTION_KEY_2).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            dataTKKhachSan = new ArrayList<>();
                            QuerySnapshot querySnapshot = task.getResult();
                            for (DocumentSnapshot documentSnapshot : querySnapshot) {
                                taiKhoanKhachSan = documentSnapshot.toObject(TaiKhoanKhachSan.class);
                                taiKhoanKhachSan.setIdTaiKhoanKhachSan(documentSnapshot.getId());
                                dataTKKhachSan.add(taiKhoanKhachSan);
                            }
                            Log.d(TAG, "Lấy dữ liệu thành công");
                            db.collection(COLLECTION_KEY_1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        dataKhachSan = new ArrayList<>();
                                        QuerySnapshot querySnapshot = task.getResult();
                                        for (DocumentSnapshot documentSnapshot : querySnapshot) {
                                            khachSan = documentSnapshot.toObject(KhachSan.class);
                                            khachSan.setMaKhachSan(documentSnapshot.getId());
                                            dataKhachSan.add(khachSan);
                                        }
                                        Log.d(TAG, "Lấy dữ liệu thành công");
                                        for (KhachSan ks : dataKhachSan) {
                                            for (TaiKhoanKhachSan tkks : dataTKKhachSan) {
                                                if (tkks.getTenTaiKhoanKhachSan().equalsIgnoreCase(ks.getTenTaiKhoanKhachSan())) {
                                                    if (tkks.getTrangThaiTaiKhoan().equals("false")) {
                                                        tkks.setTrangThaiTaiKhoan("true");
                                                        UpdateTKKhachSan(tkks);
                                                        Toast.makeText(getActivity(), "Okie, Tài khoản đã được mở khóa", Toast.LENGTH_SHORT).show();
                                                        break;
                                                    } else if (tkks.getTrangThaiTaiKhoan().equals("true")) {
                                                        Toast.makeText(getActivity(), "Tài khoản đã được mở khóa trước đây", Toast.LENGTH_SHORT).show();
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        Log.d(TAG, "Có lỗi");
                                    }
                                }

                            });
                        } else {
                            Log.d(TAG, "Có lỗi");
                        }
                    }
                });
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(KhachSanViewModel.class);
        View root = inflater.inflate(R.layout.fragment_khachsan, container, false);

        svKhachSan = root.findViewById(R.id.svKhachSan);
        btnDangXuat = root.findViewById(R.id.btnDangXuat);
        rvKhachSan = root.findViewById(R.id.rvKhachSan);
        db = FirebaseFirestore.getInstance();
        registerForContextMenu(rvKhachSan);
        svKhachSan.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterKhachSan.getFilter().filter(newText);
                return false;
            }
        });

        GetDataKhachSan();

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChiTietKhachSan.class);
                startActivity(intent);
            }
        });

        setOnClickListener();


        return root;
    }

    private void setOnClickListener() {
        listener = new AdapterKhachSan.ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ChiTietKhachSan.class);
                Bundle bundle = new Bundle();
                bundle.putString("maks", dataKhachSan.get(position).getMaKhachSan());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };
    }

    // get data from firestore, load into listview
    public void GetDataKhachSan() {
        db.collection(COLLECTION_KEY_1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    dataKhachSan = new ArrayList<>();
                    QuerySnapshot querySnapshot = task.getResult();
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        khachSan = documentSnapshot.toObject(KhachSan.class);
                        khachSan.setMaKhachSan(documentSnapshot.getId());
                        dataKhachSan.add(khachSan);
                    }
                    adapterKhachSan = new AdapterKhachSan(getActivity(), dataKhachSan, listener);
                    rvKhachSan.setAdapter(adapterKhachSan);
                    Log.d(TAG, "Lấy dữ liệu thành công");
                } else {
                    Log.d(TAG, "Có lỗi");
                }
            }
        });
    }

    //add data firestore
    public void UpdateTKKhachSan(TaiKhoanKhachSan tkks) {
        Map<String, String> map = new HashMap<>();
        map.put("trangThaiTaiKhoan", tkks.getTrangThaiTaiKhoan());
        map.put("tenTaiKhoanKhachSan", tkks.getTenTaiKhoanKhachSan());
        map.put("matKhau", tkks.getMatKhau());
        db.collection(COLLECTION_KEY_2).document(tkks.getIdTaiKhoanKhachSan()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "trạng thái được update");
            }
        });
    }
}