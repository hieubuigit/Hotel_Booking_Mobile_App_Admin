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
import android.widget.SearchView;
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
    SearchView svNguoiDung;
    Button btnDangXuat;
    ListView lvNguoiDung;
    CustomAdapterNguoiDung adapterNguoiDung;
    FirebaseFirestore db;
    NguoiDung nguoiDung;
    TaiKhoanNguoiDung taiKhoanNguoiDung;
    ArrayList<NguoiDung> dataNguoiDung;
    ArrayList<NguoiDung> listNguoiDung = new ArrayList<>();
    ArrayList<TaiKhoanNguoiDung> dataTKNguoiDung;
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

        svNguoiDung = root.findViewById(R.id.svNguoiDung);
        btnDangXuat = root.findViewById(R.id.btnDangXuat);
        lvNguoiDung = root.findViewById(R.id.lvNguoiDung);
        db = FirebaseFirestore.getInstance();
        registerForContextMenu(lvNguoiDung);
        svNguoiDung.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        GetDataNguoiDung();

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
                bundle.putString("mand", );
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        return root;
    }

    private void searchData(String query) {
        db.collection(COLLECTION_KEY_1).whereEqualTo("tenNguoiDung", query).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                listNguoiDung.clear();
                QuerySnapshot querySnapshot = task.getResult();
                for (DocumentSnapshot documentSnapshot : querySnapshot) {
                    nguoiDung = documentSnapshot.toObject(NguoiDung.class);
                    if (nguoiDung != null) {
                        nguoiDung.setIdNguoiDung(documentSnapshot.getId());
                    }
                    listNguoiDung.add(nguoiDung);
                }
                adapterNguoiDung = new CustomAdapterNguoiDung(getActivity(), R.layout.custom_item_nguoidung, listNguoiDung);
                lvNguoiDung.setAdapter(adapterNguoiDung);
                Log.d(TAG, "Lấy dữ liệu thành công");
            }
        });
    }



    // get data from firestore, load into listview
    public void GetDataNguoiDung() {
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
    }

    public void GetDataTKNguoiDung() {
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
    }

    //add data firestore
    public void UpdateTKNguoiDung(TaiKhoanNguoiDung tknd) {
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
    }
}