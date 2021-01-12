package com.chuyende.hotelbookingappofadmin.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.chuyende.hotelbookingappofadmin.ui.DangNhap;

public class CheckConnection extends BroadcastReceiver {

    public ConnectivityManager connectivityManager;

    @Override
    public void onReceive(Context context, Intent intent) {

    }

    public boolean isConnected() {
        connectivityManager = (ConnectivityManager) DangNhap.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }

        return false;
    }
}
