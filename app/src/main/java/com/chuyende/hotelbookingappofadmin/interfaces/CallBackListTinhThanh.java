package com.chuyende.hotelbookingappofadmin.interfaces;

import com.chuyende.hotelbookingappofadmin.data_model.TinhThanhPho;

import java.util.ArrayList;

public interface CallBackListTinhThanh {
    public void onDataGetListTinhThanh(ArrayList<TinhThanhPho> listTinhThanh);
}
