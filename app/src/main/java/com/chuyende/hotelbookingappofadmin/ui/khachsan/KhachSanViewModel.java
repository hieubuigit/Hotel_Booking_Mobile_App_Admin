package com.chuyende.hotelbookingappofadmin.ui.khachsan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class KhachSanViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public KhachSanViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}