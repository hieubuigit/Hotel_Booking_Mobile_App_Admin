package com.chuyende.hotelbookingappofadmin.firebase;

import android.util.Log;

import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofadmin.data_model.TinhThanhPho;
import com.chuyende.hotelbookingappofadmin.interfaces.CallBackListTinhThanh;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FireStore_TinhThanh {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static final String COLLECTION_TINH_THANH_PHO = "TinhThanhPho";
    public static final String KEY_MA_TINH_THANH_PHO = "maTinhThanhPho";
    public static final String KEY_TINH_THANH_PHO = "tinhThanhPho";

    public void readAllDataTinhThanhPho(CallBackListTinhThanh tinhThanhPhoCallback) {
        db.collection(COLLECTION_TINH_THANH_PHO).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("TTP=>", "Listen failed! Error: " + error.getMessage());
                }
                if (value != null) {
                    ArrayList<TinhThanhPho> dsTinhThanhPho = new ArrayList<TinhThanhPho>();
                    TinhThanhPho tinhThanhPho;
                    for (QueryDocumentSnapshot doc : value) {
                        tinhThanhPho = new TinhThanhPho(doc.getString(KEY_MA_TINH_THANH_PHO), doc.getString(KEY_TINH_THANH_PHO));
                        dsTinhThanhPho.add(tinhThanhPho);

                        // Test database
                        Log.d("TTP=>", tinhThanhPho.getMaTinhThanhPho() + " -- " + tinhThanhPho.getTinhThanhPho());
                    }
                    tinhThanhPhoCallback.onDataGetListTinhThanh(dsTinhThanhPho);
                } else {
                    Log.d("TTP=>", "Data is null!");
                }
            }
        });
    }
}
