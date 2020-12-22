package com.chuyende.hotelbookingappofadmin.data_model;

public class DaThanhToan {
    String maPhong, ngayThanhToan, trangThaiHoanTatThanhToan;
    int tongThanhToan;

    public DaThanhToan() {
    }

    public DaThanhToan(String maPhong, String ngayThanhToan, String trangThaiHoanTatThanhToan, int tongThanhToan) {
        this.maPhong = maPhong;
        this.ngayThanhToan = ngayThanhToan;
        this.trangThaiHoanTatThanhToan = trangThaiHoanTatThanhToan;
        this.tongThanhToan = tongThanhToan;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(String ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }

    public String getTrangThaiHoanTatThanhToan() {
        return trangThaiHoanTatThanhToan;
    }

    public void setTrangThaiHoanTatThanhToan(String trangThaiHoanTatThanhToan) {
        this.trangThaiHoanTatThanhToan = trangThaiHoanTatThanhToan;
    }

    public int getTongThanhToan() {
        return tongThanhToan;
    }

    public void setTongThanhToan(int tongThanhToan) {
        this.tongThanhToan = tongThanhToan;
    }
}
