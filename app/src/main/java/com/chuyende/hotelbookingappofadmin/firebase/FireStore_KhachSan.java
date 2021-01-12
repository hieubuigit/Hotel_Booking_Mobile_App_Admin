package com.chuyende.hotelbookingappofadmin.firebase;

import android.util.Log;

import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofadmin.data_model.KhachSan;
import com.chuyende.hotelbookingappofadmin.interfaces.CallBackKhachSanByID;
import com.chuyende.hotelbookingappofadmin.interfaces.CallBackListKhachSan;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FireStore_KhachSan {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    KhachSan khachSan;
    ArrayList<KhachSan> listKhachSan;
    public static final String COLLECTION_KHACH_SAN = "KhachSan";


    public void getAllKhachSan(CallBackListKhachSan callBackListKhachSan) {
        db.collection(COLLECTION_KHACH_SAN).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("DTT ---->", "value is null");
                }
                if (value != null) {
                    khachSan = new KhachSan();
                    listKhachSan = new ArrayList<>();
                    for (DocumentSnapshot doc : value) {
                        khachSan = doc.toObject(KhachSan.class);
                        listKhachSan.add(khachSan);
                        Log.d("Khách sạn ---->", "mã khách sạn: " + khachSan.getMaKhachSan() + ", tên khách sạn: " + khachSan.getTenKhachSan());
                    }
                    callBackListKhachSan.onDataCallBackListKhachSan(listKhachSan);
                }
            }
        });
    }

    public void getKhachSanByID(String id, CallBackKhachSanByID callBackKhachSanByID) {
        db.collection(COLLECTION_KHACH_SAN).document(id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("DTT ---->", "value is null");
                } else {
                    khachSan = new KhachSan();
                    khachSan = value.toObject(KhachSan.class);
                    Log.d("Khách sạn ---->", "mã khách sạn: " + khachSan.getMaKhachSan() + ", tên khách sạn: " + khachSan.getTenKhachSan());
                    callBackKhachSanByID.onDataCallBackKhachSanbyID(khachSan);
                }
            }
        });
    }
}
