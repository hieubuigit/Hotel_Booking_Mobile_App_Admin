package com.chuyende.hotelbookingappofadmin.ui.khachsan;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofadmin.R;
import com.chuyende.hotelbookingappofadmin.adapter.AdapterKhachSan;
import com.chuyende.hotelbookingappofadmin.data_model.KhachSan;
import com.chuyende.hotelbookingappofadmin.data_model.TaiKhoanKhachSan;
import com.chuyende.hotelbookingappofadmin.data_model.TinhThanhPho;
import com.chuyende.hotelbookingappofadmin.firebase.FireStore_KhachSan;
import com.chuyende.hotelbookingappofadmin.firebase.FireStore_TinhThanh;
import com.chuyende.hotelbookingappofadmin.interfaces.CallBackListKhachSan;
import com.chuyende.hotelbookingappofadmin.interfaces.CallBackListTinhThanh;
import com.chuyende.hotelbookingappofadmin.ui.DangNhap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class FragmentKhachSan extends Fragment {
    private static final String COLLECTION_KEY_1 = "KhachSan";
    private static final String COLLECTION_KEY_2 = "TaiKhoanKhachSan";
    private static final String TRANG_THAI_TAI_KHOAN = "trangThaiTaiKhoan";
    private static final String TEN_TAI_KHOAN_KHACH_SAN = "tenTaiKhoanKhachSan";
    private static final String MAT_KHAU = "matKhau";
    private static final String TRUE = "true";
    private static final String FALSE = "false";

    private AdapterKhachSan.ItemClickListener listener;

    SearchView svKhachSan;
    Spinner spLocTinhThanh;
    Button btnDangXuat;
    RecyclerView rvKhachSan;
    AdapterKhachSan adapterKhachSan;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FireStore_TinhThanh dbTinhThanh = new FireStore_TinhThanh();
    FireStore_KhachSan dbKhachSan = new FireStore_KhachSan();

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
                try {
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
                                try {
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
                                                            if (tkks.getTrangThaiTaiKhoan().equals(TRUE)) {
                                                                tkks.setTrangThaiTaiKhoan(FALSE);
                                                                UpdateTKKhachSan(tkks);
                                                                Toast.makeText(getActivity(), "Okie, Tài khoản đã được khóa", Toast.LENGTH_SHORT).show();
                                                                break;
                                                            } else if (tkks.getTrangThaiTaiKhoan().equals(FALSE)) {
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
                                } catch (Exception e) {
                                    Log.d(TAG, e.toString());
                                }
                            } else {
                                Log.d(TAG, "Có lỗi");
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
                return true;
            case R.id.unlock_user:
                try {
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
                                try {
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
                                                            if (tkks.getTrangThaiTaiKhoan().equals(FALSE)) {
                                                                tkks.setTrangThaiTaiKhoan(TRUE);
                                                                UpdateTKKhachSan(tkks);
                                                                Toast.makeText(getActivity(), "Okie, Tài khoản đã được mở khóa", Toast.LENGTH_SHORT).show();
                                                                break;
                                                            } else if (tkks.getTrangThaiTaiKhoan().equals(TRUE)) {
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
                                } catch (Exception e) {
                                    Log.d(TAG, e.toString());
                                }
                            } else {
                                Log.d(TAG, "Có lỗi");
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
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
        spLocTinhThanh = root.findViewById(R.id.spLocTinhThanh);
        btnDangXuat = root.findViewById(R.id.btnDangXuat);
        rvKhachSan = root.findViewById(R.id.rvKhachSan);


        getDataAndFilterKhachSan();

        dbTinhThanh.readAllDataTinhThanhPho(new CallBackListTinhThanh() {
            @Override
            public void onDataGetListTinhThanh(ArrayList<TinhThanhPho> listTinhThanh) {
                ArrayList<String> tinhThanhList = new ArrayList<>();
                tinhThanhList.add("Tỉnh/Thành phố");
                for (TinhThanhPho ttp : listTinhThanh) {
                    tinhThanhList.add(ttp.getTinhThanhPho());
                }
                ArrayAdapter<String> adapterTinhThanh = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, tinhThanhList);
                spLocTinhThanh.setAdapter(adapterTinhThanh);
                adapterTinhThanh.notifyDataSetChanged();
            }

        });

        registerForContextMenu(rvKhachSan);

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn có muốn thoát không ?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), DangNhap.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
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

    public void getDataAndFilterKhachSan() {
        dbKhachSan.getAllKhachSan(new CallBackListKhachSan() {
            @Override
            public void onDataCallBackListKhachSan(ArrayList<KhachSan> listKhachSan) {
                dataKhachSan = new ArrayList<>();
                for (KhachSan ks : listKhachSan) {
                    dataKhachSan.add(ks);
                }

                /// set adapter for recyclerview
                adapterKhachSan = new AdapterKhachSan(dataKhachSan, getActivity(), listener);
                rvKhachSan.setAdapter(adapterKhachSan);
                adapterKhachSan.notifyDataSetChanged();


                /// filter by spinner tinh/thanh and search bar
                svKhachSan.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        adapterKhachSan.searchAndFilter(newText, spLocTinhThanh.getSelectedItem().toString());
                        return false;
                    }
                });

                spLocTinhThanh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        adapterKhachSan.searchAndFilter(svKhachSan.getQuery().toString(), spLocTinhThanh.getSelectedItem().toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });
    }

    //add data tài khoản khách sạn firestore
    public void UpdateTKKhachSan(TaiKhoanKhachSan tkks) {
        Map<String, String> map = new HashMap<>();
        map.put(TRANG_THAI_TAI_KHOAN, tkks.getTrangThaiTaiKhoan());
        map.put(TEN_TAI_KHOAN_KHACH_SAN, tkks.getTenTaiKhoanKhachSan());
        map.put(MAT_KHAU, tkks.getMatKhau());
        db.collection(COLLECTION_KEY_2).document(tkks.getIdTaiKhoanKhachSan()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "trạng thái được update");
            }
        });
    }


}