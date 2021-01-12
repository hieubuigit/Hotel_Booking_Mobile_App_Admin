package com.chuyende.hotelbookingappofadmin.ui.nguoidung;

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
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofadmin.R;
import com.chuyende.hotelbookingappofadmin.adapter.AdapterNguoiDung;
import com.chuyende.hotelbookingappofadmin.data_model.NguoiDung;
import com.chuyende.hotelbookingappofadmin.data_model.TaiKhoanNguoiDung;
import com.chuyende.hotelbookingappofadmin.firebase.FireStore_NguoiDung;
import com.chuyende.hotelbookingappofadmin.interfaces.CallBackListNguoiDung;
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

public class FragmentNguoiDung extends Fragment {
    private final String COLLECTION_KEY_1 = "NguoiDung";
    private final String COLLECTION_KEY_2 = "TaiKhoanNguoiDung";
    private AdapterNguoiDung.ItemClickListener listener;
    SearchView svNguoiDung;
    Button btnDangXuat;
    RecyclerView rvNguoiDung;
    AdapterNguoiDung adapterNguoiDung;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FireStore_NguoiDung dbNguoiDung = new FireStore_NguoiDung();
    NguoiDung nguoiDung;
    TaiKhoanNguoiDung taiKhoanNguoiDung;
    ArrayList<NguoiDung> dataNguoiDung;
    ArrayList<TaiKhoanNguoiDung> dataTKNguoiDung;
    private NguoiDungViewModel dashboardViewModel;

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_menu_nguoidung, menu);
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
                                dataTKNguoiDung = new ArrayList<>();
                                QuerySnapshot querySnapshot = task.getResult();
                                for (DocumentSnapshot documentSnapshot : querySnapshot) {
                                    taiKhoanNguoiDung = documentSnapshot.toObject(TaiKhoanNguoiDung.class);
                                    taiKhoanNguoiDung.setIdTKNguoiDung(documentSnapshot.getId());
                                    dataTKNguoiDung.add(taiKhoanNguoiDung);
                                }
                                Log.d(TAG, "Lấy dữ liệu thành công");
                                try {
                                    db.collection(COLLECTION_KEY_1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                dataNguoiDung = new ArrayList<>();
                                                QuerySnapshot querySnapshot = task.getResult();
                                                for (DocumentSnapshot documentSnapshot : querySnapshot) {
                                                    nguoiDung = documentSnapshot.toObject(NguoiDung.class);
                                                    nguoiDung.setMaNguoiDung(documentSnapshot.getId());
                                                    dataNguoiDung.add(nguoiDung);
                                                }
                                                Log.d(TAG, "Lấy dữ liệu thành công");
                                                for (NguoiDung nd : dataNguoiDung) {
                                                    for (TaiKhoanNguoiDung tknd : dataTKNguoiDung) {
                                                        if (tknd.getTenTaiKhoan().equalsIgnoreCase(nd.getTenTaiKhoan())) {
                                                            if (tknd.getTrangThaiTaiKhoan().equals("true")) {
                                                                tknd.setTrangThaiTaiKhoan("false");
                                                                UpdateTKNguoiDung(tknd);
                                                                Toast.makeText(getActivity(), "Okie, Tài khoản đã được khóa", Toast.LENGTH_SHORT).show();
                                                                break;
                                                            } else if (tknd.getTrangThaiTaiKhoan().equals("false")) {
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
                                dataTKNguoiDung = new ArrayList<>();
                                QuerySnapshot querySnapshot = task.getResult();
                                for (DocumentSnapshot documentSnapshot : querySnapshot) {
                                    taiKhoanNguoiDung = documentSnapshot.toObject(TaiKhoanNguoiDung.class);
                                    taiKhoanNguoiDung.setIdTKNguoiDung(documentSnapshot.getId());
                                    dataTKNguoiDung.add(taiKhoanNguoiDung);
                                }
                                Log.d(TAG, "Lấy dữ liệu thành công");
                                try {
                                    db.collection(COLLECTION_KEY_1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                dataNguoiDung = new ArrayList<>();
                                                QuerySnapshot querySnapshot = task.getResult();
                                                for (DocumentSnapshot documentSnapshot : querySnapshot) {
                                                    nguoiDung = documentSnapshot.toObject(NguoiDung.class);
                                                    nguoiDung.setMaNguoiDung(documentSnapshot.getId());
                                                    dataNguoiDung.add(nguoiDung);
                                                }
                                                Log.d(TAG, "Lấy dữ liệu thành công");
                                                for (NguoiDung nd : dataNguoiDung) {
                                                    for (TaiKhoanNguoiDung tknd : dataTKNguoiDung) {
                                                        if (tknd.getTenTaiKhoan().equalsIgnoreCase(nd.getTenTaiKhoan())) {
                                                            if (tknd.getTrangThaiTaiKhoan().equals("false")) {
                                                                tknd.setTrangThaiTaiKhoan("true");
                                                                UpdateTKNguoiDung(tknd);
                                                                Toast.makeText(getActivity(), "Okie, Tài khoản đã được mở khóa", Toast.LENGTH_SHORT).show();
                                                                break;
                                                            } else if (tknd.getTrangThaiTaiKhoan().equals("true")) {
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
                new ViewModelProvider(this).get(NguoiDungViewModel.class);
        View root = inflater.inflate(R.layout.fragment_nguoidung, container, false);

        svNguoiDung = root.findViewById(R.id.svNguoiDung);
        btnDangXuat = root.findViewById(R.id.btnDangXuat);
        rvNguoiDung = root.findViewById(R.id.rvNguoiDung);

        registerForContextMenu(rvNguoiDung);

        svNguoiDung.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterNguoiDung.getFilter().filter(newText);
                return false;
            }
        });

        GetDataNguoiDung();

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
        listener = new AdapterNguoiDung.ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ChiTietNguoiDung.class);
                Bundle bundle = new Bundle();
                bundle.putString("mand", dataNguoiDung.get(position).getMaNguoiDung());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        };
    }

    // get data from firestore, load into recyclerview
    public void GetDataNguoiDung() {
        try {
            dbNguoiDung.getAllNguoiDung(new CallBackListNguoiDung() {
                @Override
                public void onDataCallBackListNguoiDung(ArrayList<NguoiDung> listNguoiDung) {
                    dataNguoiDung = new ArrayList<>();
                    for (NguoiDung nd : listNguoiDung) {
                        dataNguoiDung.add(nd);
                    }
                    adapterNguoiDung = new AdapterNguoiDung(getActivity(), dataNguoiDung, listener);
                    rvNguoiDung.setAdapter(adapterNguoiDung);
                    adapterNguoiDung.notifyDataSetChanged();
                }
            });
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    //add data firestore
    public void UpdateTKNguoiDung(TaiKhoanNguoiDung tknd) {
        try {
            Map<String, String> map = new HashMap<>();
            map.put("trangThaiTaiKhoan", tknd.getTrangThaiTaiKhoan());
            map.put("tenTaiKhoan", tknd.getTenTaiKhoan());
            map.put("email", tknd.getEmail());
            map.put("matKhau", tknd.getMatKhau());
            map.put("soDienThoai", tknd.getSoDienThoai());
            db.collection(COLLECTION_KEY_2).document(tknd.getIdTKNguoiDung()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "trạng thái được update");
                }
            });
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }


    }
}