package com.chuyende.hotelbookingappofadmin.ui.nguoidung;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.chuyende.hotelbookingappofadmin.R;
import com.chuyende.hotelbookingappofadmin.adapter.CustomAdapterNguoiDung;
import com.chuyende.hotelbookingappofadmin.data_model.NguoiDung;
import com.chuyende.hotelbookingappofadmin.data_model.TaiKhoanNguoiDung;
import com.chuyende.hotelbookingappofadmin.firebase.FireStore_NguoiDung;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class FragmentNguoiDung extends Fragment {
    private final String COLLECTION_KEY_1 = "NguoiDung";
    private final String COLLECTION_KEY_2 = "TaiKhoanNguoiDung";
    EditText edtSearchND;
    ImageButton btnSearchND;
    Button btnDangXuat;
    ListView lvNguoiDung;
    FireStore_NguoiDung fbNguoiDung = new FireStore_NguoiDung();
    CustomAdapterNguoiDung adapterNguoiDung;
    FirebaseFirestore db;
    NguoiDung nguoiDung;
    TaiKhoanNguoiDung taiKhoanNguoiDung;
    ArrayList<NguoiDung> dataNguoiDung;
    ArrayList<TaiKhoanNguoiDung> dataTKNguoiDung;
    Query tenNguoiDung;
    private NguoiDungViewModel dashboardViewModel;

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        DocumentReference docRef = db.collection(COLLECTION_KEY_2).document("TKND01");
        Map<String, Object> map = new HashMap<>();
        switch (item.getItemId()) {
            case R.id.lock_user:
                db.collection(COLLECTION_KEY_2).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            dataTKNguoiDung = new ArrayList<>();
                            QuerySnapshot querySnapshot = task.getResult();
                            for (DocumentSnapshot documentSnapshot : querySnapshot) {
                                taiKhoanNguoiDung = documentSnapshot.toObject(TaiKhoanNguoiDung.class);
                                if (taiKhoanNguoiDung != null) {
                                    taiKhoanNguoiDung.setIdTKNguoiDung(documentSnapshot.getId());
                                }
                                dataTKNguoiDung.add(taiKhoanNguoiDung);
                            }
                            Log.d(TAG, "Lấy dữ liệu thành công");
                            db.collection(COLLECTION_KEY_1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        dataNguoiDung = new ArrayList<>();
                                        QuerySnapshot querySnapshot = task.getResult();
                                        for (DocumentSnapshot documentSnapshot : querySnapshot) {
                                            nguoiDung = documentSnapshot.toObject(NguoiDung.class);
                                            if (nguoiDung != null) {
                                                nguoiDung.setIdNguoiDung(documentSnapshot.getId());
                                            }
                                            dataNguoiDung.add(nguoiDung);
                                        }
                                        Log.d(TAG, "Lấy dữ liệu thành công");
                                        for (NguoiDung nd : dataNguoiDung) {
                                            for (TaiKhoanNguoiDung tknd : dataTKNguoiDung) {
                                                if (tknd.getTenTaiKhoan().equalsIgnoreCase(nd.getTenTaiKhoan())) {
                                                    if (tknd.getTrangThaiTaiKhoan().equals("true")) {
                                                        tknd.setTrangThaiTaiKhoan("false");
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
                            dataTKNguoiDung = new ArrayList<>();
                            QuerySnapshot querySnapshot = task.getResult();
                            for (DocumentSnapshot documentSnapshot : querySnapshot) {
                                taiKhoanNguoiDung = documentSnapshot.toObject(TaiKhoanNguoiDung.class);
                                if (taiKhoanNguoiDung != null) {
                                    taiKhoanNguoiDung.setIdTKNguoiDung(documentSnapshot.getId());
                                }
                                dataTKNguoiDung.add(taiKhoanNguoiDung);
                            }
                            Log.d(TAG, "Lấy dữ liệu thành công");
                            db.collection(COLLECTION_KEY_1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        dataNguoiDung = new ArrayList<>();
                                        QuerySnapshot querySnapshot = task.getResult();
                                        for (DocumentSnapshot documentSnapshot : querySnapshot) {
                                            nguoiDung = documentSnapshot.toObject(NguoiDung.class);
                                            if (nguoiDung != null) {
                                                nguoiDung.setIdNguoiDung(documentSnapshot.getId());
                                            }
                                            dataNguoiDung.add(nguoiDung);
                                        }
                                        Log.d(TAG, "Lấy dữ liệu thành công");
                                        for (NguoiDung nd : dataNguoiDung) {
                                            for (TaiKhoanNguoiDung tknd : dataTKNguoiDung) {
                                                if (tknd.getTenTaiKhoan().equalsIgnoreCase(nd.getTenTaiKhoan())) {
                                                    if (tknd.getTrangThaiTaiKhoan().equals("false")) {
                                                        tknd.setTrangThaiTaiKhoan("true");
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
                new ViewModelProvider(this).get(NguoiDungViewModel.class);
        View root = inflater.inflate(R.layout.fragment_nguoidung, container, false);

        edtSearchND = root.findViewById(R.id.edtTimKiemNguoiDung);
        btnSearchND = root.findViewById(R.id.btnTimKiemNguoiDung);
        btnDangXuat = root.findViewById(R.id.btnDangXuat);
        lvNguoiDung = root.findViewById(R.id.lvNguoiDung);
        db = FirebaseFirestore.getInstance();
        registerForContextMenu(lvNguoiDung);
        db.collection(COLLECTION_KEY_1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    dataNguoiDung = new ArrayList<>();
                    QuerySnapshot querySnapshot = task.getResult();
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        nguoiDung = documentSnapshot.toObject(NguoiDung.class);
                        if (nguoiDung != null) {
                            nguoiDung.setIdNguoiDung(documentSnapshot.getId());
                        }
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

        edtSearchND.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

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
                bundle.putString("mand", nguoiDung.getIdNguoiDung());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        return root;
    }

    public ArrayList<NguoiDung> LayDSNguoiDung() {
        db.collection(COLLECTION_KEY_1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    dataNguoiDung = new ArrayList<>();
                    QuerySnapshot querySnapshot = task.getResult();
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        nguoiDung = documentSnapshot.toObject(NguoiDung.class);
                        dataNguoiDung.add(nguoiDung);
                    }

                    Log.d(TAG, "Lấy dữ liệu thành công");
                } else {
                    Log.d(TAG, "Có lỗi");
                }
            }
        });
        return dataNguoiDung;
    }

    public ArrayList<TaiKhoanNguoiDung> LayDSTKNguoiDung() {
        db.collection(COLLECTION_KEY_2).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    dataTKNguoiDung = new ArrayList<>();
                    QuerySnapshot querySnapshot = task.getResult();
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        taiKhoanNguoiDung = documentSnapshot.toObject(TaiKhoanNguoiDung.class);
                        dataTKNguoiDung.add(taiKhoanNguoiDung);
                    }
                    Log.d(TAG, "Lấy dữ liệu thành công");
                } else {
                    Log.d(TAG, "Có lỗi");
                }
            }
        });
        return dataTKNguoiDung;
    }

    public void search(String str) {
        ArrayList<NguoiDung> listNguoiDung = new ArrayList<>();
        for (NguoiDung nguoiDung : dataNguoiDung) {
            if (nguoiDung.getTenNguoiDung().toLowerCase().contains(str.toLowerCase())) {
                listNguoiDung.add(nguoiDung);
            }
        }
        adapterNguoiDung = new CustomAdapterNguoiDung(getActivity(), R.layout.custom_item_nguoidung, dataNguoiDung);
        lvNguoiDung.setAdapter(adapterNguoiDung);
    }
}