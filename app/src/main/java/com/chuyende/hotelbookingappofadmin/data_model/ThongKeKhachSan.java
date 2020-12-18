package com.chuyende.hotelbookingappofadmin.data_model;

public class ThongKeKhachSan {
    String maKhachSan, tenKhachSan, ngayThanhToan, tongThanhToan, ngayDatPhong, soLuotDat;

    public ThongKeKhachSan() {
    }

    public ThongKeKhachSan(String maKhachSan, String tenKhachSan, String ngayThanhToan, String tongThanhToan, String ngayDatPhong, String soLuotDat) {
        this.maKhachSan = maKhachSan;
        this.tenKhachSan = tenKhachSan;
        this.ngayThanhToan = ngayThanhToan;
        this.tongThanhToan = tongThanhToan;
        this.ngayDatPhong = ngayDatPhong;
        this.soLuotDat = soLuotDat;
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

    public String getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(String ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getTongThanhToan() {
        return tongThanhToan;
    }

    public void setTongThanhToan(String tongThanhToan) {
        this.tongThanhToan = tongThanhToan;
    }

    public String getNgayDatPhong() {
        return ngayDatPhong;
    }

    public void setNgayDatPhong(String ngayDatPhong) {
        this.ngayDatPhong = ngayDatPhong;
    }

    public String getSoLuotDat() {
        return soLuotDat;
    }

    public void setSoLuotDat(String soLuotDat) {
        this.soLuotDat = soLuotDat;
    }
}
