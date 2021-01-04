package com.chuyende.hotelbookingappofadmin.firebase;

import android.util.Log;

import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofadmin.data_model.TaiKhoanKhachSan;
import com.chuyende.hotelbookingappofadmin.interfaces.CallBackListTKKhachSan;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Firestore_TKKhachSan {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static final String COLLECTION_TAI_KHOAN_KHACH_SAN = "TaiKhoanKhachSan";

    public void getAllTKKhachSan(CallBackListTKKhachSan callBackListTKKhachSan) {
        db.collection(COLLECTION_TAI_KHOAN_KHACH_SAN).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("DTT ---->", "value is null");
                } else {
                    TaiKhoanKhachSan taiKhoanKhachSan = new TaiKhoanKhachSan();
                    ArrayList<TaiKhoanKhachSan> listTKKhachSan = new ArrayList<>();
                    for (DocumentSnapshot doc : value) {
                        taiKhoanKhachSan = doc.toObject(TaiKhoanKhachSan.class);
                        listTKKhachSan.add(taiKhoanKhachSan);
                        Log.d("Khách sạn ---->", "mã khách sạn: " + taiKhoanKhachSan.getIdTaiKhoanKhachSan() + ", tên khách sạn: " + taiKhoanKhachSan.getTenTaiKhoanKhachSan());
                    }
                    callBackListTKKhachSan.onDataCallBackListTKKhachSan(listTKKhachSan);
                }
            }
        });
    }
}
