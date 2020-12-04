package com.chuyende.hotelbookingappofadmin.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.chuyende.hotelbookingappofadmin.data_model.NguoiDung;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class FireStore_NguoiDung {
    FirebaseFirestore db;
    private final String COLLECTION_KEY = "NguoiDung";

    public ArrayList<NguoiDung> getListAllNguoiDung() {
        db = FirebaseFirestore.getInstance();
        ArrayList<NguoiDung> data = new ArrayList<>();
        db.collection(COLLECTION_KEY).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    for (DocumentSnapshot documentSnapshot : querySnapshot) {
                        NguoiDung nguoiDung = new NguoiDung();
                        nguoiDung.setMaNguoiDung(documentSnapshot.getString("maNguoiDung"));
                        nguoiDung.setTenNguoiDung(documentSnapshot.getString("tenNguoiDung"));
                        nguoiDung.setGioiTinh(documentSnapshot.getString("gioiTinh"));
                        nguoiDung.setNgaySinh(documentSnapshot.getString("ngaySinh"));
                        nguoiDung.setQuocTich(documentSnapshot.getString("quocTich"));
                        nguoiDung.setCmnd(documentSnapshot.getString("cmnd"));
                        nguoiDung.setDiaChi(documentSnapshot.getString("diaChi"));
                        nguoiDung.setEmail(documentSnapshot.getString("email"));
                        nguoiDung.setSoDT(documentSnapshot.getString("soDienThoai"));
                        nguoiDung.setUrlAnhDaiDien(documentSnapshot.getString("anhDaiDien"));
                        data.add(nguoiDung);
                    }
                    Log.d(TAG, "Lấy dữ liệu thành công");
                } else {
                    Log.d(TAG, "Có lỗi");
                }
            }
        });
        return data;
    }

    public ArrayList<NguoiDung> getNguoiDung(String maNguoiDung) {
        db = FirebaseFirestore.getInstance();
        ArrayList<NguoiDung> data = new ArrayList<>();
        db.collection(COLLECTION_KEY).document(maNguoiDung).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        NguoiDung nguoiDung = new NguoiDung();
                        nguoiDung.setMaNguoiDung(documentSnapshot.getString("maNguoiDung"));
                        nguoiDung.setTenNguoiDung(documentSnapshot.getString("tenNguoiDung"));
                        nguoiDung.setGioiTinh(documentSnapshot.getString("gioiTinh"));
                        nguoiDung.setNgaySinh(documentSnapshot.getString("ngaySinh"));
                        nguoiDung.setQuocTich(documentSnapshot.getString("quocTich"));
                        nguoiDung.setCmnd(documentSnapshot.getString("cmnd"));
                        nguoiDung.setDiaChi(documentSnapshot.getString("diaChi"));
                        nguoiDung.setEmail(documentSnapshot.getString("email"));
                        nguoiDung.setSoDT(documentSnapshot.getString("soDienThoai"));
                        data.add(nguoiDung);
                        Log.d(TAG, "Tìm thấy document");
                    } else {
                        Log.d(TAG, "Không tìm thấy document");
                    }
                } else {
                    Log.d(TAG, "Có lỗi", task.getException());
                }
            }
        });
        return data;
    }
}
