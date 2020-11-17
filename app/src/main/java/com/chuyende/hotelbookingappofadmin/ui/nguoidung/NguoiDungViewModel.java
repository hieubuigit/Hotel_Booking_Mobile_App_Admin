package com.chuyende.hotelbookingappofadmin.ui.nguoidung;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NguoiDungViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NguoiDungViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}