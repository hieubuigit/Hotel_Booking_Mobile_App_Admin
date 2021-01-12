package com.chuyende.hotelbookingappofadmin.firebase;

import android.util.Log;

import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofadmin.data_model.NguoiDung;
import com.chuyende.hotelbookingappofadmin.interfaces.CallBackListNguoiDung;
import com.chuyende.hotelbookingappofadmin.interfaces.CallBackNguoiDungByID;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FireStore_NguoiDung {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    NguoiDung nguoiDung;
    ArrayList<NguoiDung> listNguoiDung;
    public static final String COLLECTION_NGUOI_DUNG = "NguoiDung";

    public void getAllNguoiDung(CallBackListNguoiDung callBackListNguoiDung) {
        db.collection(COLLECTION_NGUOI_DUNG).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("ND ---->", "value is null");
                }
                if (value != null) {
                    nguoiDung = new NguoiDung();
                    listNguoiDung = new ArrayList<>();
                    for (DocumentSnapshot doc : value) {
                        nguoiDung = doc.toObject(NguoiDung.class);
                        listNguoiDung.add(nguoiDung);
                        Log.d("Người dùng ---->", "mã người dùng: " + nguoiDung.getMaNguoiDung() + ", tên người dùng: " + nguoiDung.getTenNguoiDung());
                    }
                    callBackListNguoiDung.onDataCallBackListNguoiDung(listNguoiDung);
                }
            }
        });
    }

    public void getNguoiDungByID(String id, CallBackNguoiDungByID callBackNguoiDungByID) {
        db.collection(COLLECTION_NGUOI_DUNG).document(id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("DTT ---->", "value is null");
                } else {
                    nguoiDung = new NguoiDung();
                    nguoiDung = value.toObject(NguoiDung.class);
                    Log.d("Người dùng ---->", "mã người dùng: " + nguoiDung.getMaNguoiDung() + ", tên người dùng: " + nguoiDung.getTenNguoiDung());
                    callBackNguoiDungByID.onDataCallBackNguoiDungByID(nguoiDung);
                }
            }
        });
    }
}
