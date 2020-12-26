package com.chuyende.hotelbookingappofadmin.interfaces;

import com.chuyende.hotelbookingappofadmin.data_model.DaThanhToan;
import com.chuyende.hotelbookingappofadmin.data_model.KhachSan;

import java.util.ArrayList;

public interface CallBackListThanhToan {
    public void onDataCallBackDaThanhToan(ArrayList<DaThanhToan> listDaThanhToan);
}
