package com.chuyende.hotelbookingappofadmin.data_model;

public class ThongKeLuotDat {
    String maKhachSan, tenKhachSan, thangDatPhong;
    int soLuotDatThanhCong;

    public ThongKeLuotDat() {
    }

    public ThongKeLuotDat(String maKhachSan, String tenKhachSan, String thangDatPhong, int soLuotDatThanhCong) {
        this.maKhachSan = maKhachSan;
        this.tenKhachSan = tenKhachSan;
        this.thangDatPhong = thangDatPhong;
        this.soLuotDatThanhCong = soLuotDatThanhCong;
    }

    public String getMaKhachSan() {
        return maKhachSan;
    }

    public void setMaKhachSan(String maKhachSan) {
        this.maKhachSan = maKhachSan;
    }

    public String getTenKhachSan() {
        return tenKhachSan;
    }

    public void setTenKhachSan(String tenKhachSan) {
        this.tenKhachSan = tenKhachSan;
    }

    public String getThangDatPhong() {
        return thangDatPhong;
    }

    public void setThangDatPhong(String thangDatPhong) {
        this.thangDatPhong = thangDatPhong;
    }

    public int getSoLuotDatThanhCong() {
        return soLuotDatThanhCong;
    }

    public void setSoLuotDatThanhCong(int soLuotDatThanhCong) {
        this.soLuotDatThanhCong = soLuotDatThanhCong;
    }
}
