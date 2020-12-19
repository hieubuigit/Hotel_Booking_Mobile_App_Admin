package com.chuyende.hotelbookingappofadmin.data_model;

public class DaDat {
    String maDat, maPhong, ngayDatPhong;

    public DaDat() {
    }

    public DaDat(String maDat, String maPhong, String ngayDatPhong) {
        this.maDat = maDat;
        this.maPhong = maPhong;
        this.ngayDatPhong = ngayDatPhong;
    }

    public String getMaDat() {
        return maDat;
    }

    public void setMaDat(String maDat) {
        this.maDat = maDat;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getNgayDatPhong() {
        return ngayDatPhong;
    }

    public void setNgayDatPhong(String ngayDatPhong) {
        this.ngayDatPhong = ngayDatPhong;
    }
}
