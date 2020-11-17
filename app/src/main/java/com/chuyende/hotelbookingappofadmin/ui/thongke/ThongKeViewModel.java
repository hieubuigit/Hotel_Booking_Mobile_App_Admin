package com.chuyende.hotelbookingappofadmin.ui.thongke;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ThongKeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ThongKeViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}