package com.chuyende.hotelbookingappofadmin.firebase;

import android.util.Log;

import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofadmin.data_model.KhachSan;
import com.chuyende.hotelbookingappofadmin.data_model.Phong;
import com.chuyende.hotelbookingappofadmin.interfaces.CallBackListPhongByKSID;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class Firestore_Phong {
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public static final String COLLECTION_PHONG = "Phong";
    public static final String MA_KHACH_SAN = "maKhachSan";

    public void getAllPhongByMaKS(String id, CallBackListPhongByKSID callBackListPhongByKSID) {
        db.collection(COLLECTION_PHONG).whereEqualTo(MA_KHACH_SAN, id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("DTT ---->", "value is null");
                }
                else {
                    Phong phong = new Phong();
                    ArrayList<Phong> listPhong = new ArrayList<>();
                    for (DocumentSnapshot doc : value) {
                        phong = doc.toObject(Phong.class);
                        listPhong.add(phong);
                    }
                }
            }
        });
    }
}
