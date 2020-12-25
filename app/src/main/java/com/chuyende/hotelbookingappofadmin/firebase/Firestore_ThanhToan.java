package com.chuyende.hotelbookingappofadmin.firebase;

import android.util.Log;

import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofadmin.interfaces.CallBackListThang;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Firestore_ThanhToan {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public final static String DA_THANH_TOAN = "DaThanhToan";
    public final static String TRANG_THAI_THANH_TOAN = "trangThaiHoanTatThanhToan";
    public final static String NGAY_THANH_TOAN = "ngayThanhToan";


    public void readAllThang(CallBackListThang callBackListThang) {
        db.collection(DA_THANH_TOAN).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null)
                    Log.d("DTT ---->", "value is null");
                else {
                    ArrayList<String> listThang = new ArrayList<>();
                    String thang = "";
                    for (DocumentSnapshot doc : value) {
                        if (doc.getString(TRANG_THAI_THANH_TOAN).equals("true")) {
                            thang = doc.getString(NGAY_THANH_TOAN).substring(3, 10);
                            listThang.add(thang);
                        }
                        Log.d("DTT ---->", thang);
                    }
                    callBackListThang.onDataGetListTinhThanh(listThang);

                }
            }
        });
    }
}
