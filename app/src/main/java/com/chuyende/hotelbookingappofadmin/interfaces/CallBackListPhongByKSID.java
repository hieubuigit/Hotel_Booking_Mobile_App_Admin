package com.chuyende.hotelbookingappofadmin.interfaces;

import com.chuyende.hotelbookingappofadmin.data_model.Phong;

import java.util.ArrayList;

public interface CallBackListPhongByKSID {
    public void onDataGetListPhongByKSID(ArrayList<Phong> listPhong);
}
