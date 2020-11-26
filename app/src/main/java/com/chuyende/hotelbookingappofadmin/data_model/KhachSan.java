package com.chuyende.hotelbookingappofadmin.data_model;

import java.util.Arrays;

public class KhachSan {
    String maKhachSan, tenKhachSan, diaChi, thoiGian, maThanhToan, luotDat, tongDoanhThuKS, urlAnhKhachSan;

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

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getMaThanhToan() {
        return maThanhToan;
    }

    public void setMaThanhToan(String maThanhToan) {
        this.maThanhToan = maThanhToan;
    }

    public String getLuotDat() {
        return luotDat;
    }

    public void setLuotDat(String luotDat) {
        this.luotDat = luotDat;
    }

    public String getTongDoanhThuKS() {
        return tongDoanhThuKS;
    }

    public void setTongDoanhThuKS(String tongDoanhThuKS) {
        this.tongDoanhThuKS = tongDoanhThuKS;
    }

    public String getUrlAnhKhachSan() {
        return urlAnhKhachSan;
    }

    public void setUrlAnhKhachSan(String urlAnhKhachSan) {
        this.urlAnhKhachSan = urlAnhKhachSan;
    }

    @Override
    public String toString() {
        return "KhachSan{" +
                "maKhachSan='" + maKhachSan + '\'' +
                ", tenKhachSan='" + tenKhachSan + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", thoiGian='" + thoiGian + '\'' +
                ", maThanhToan='" + maThanhToan + '\'' +
                ", luotDat='" + luotDat + '\'' +
                ", tongDoanhThuKS='" + tongDoanhThuKS + '\'' +
                ", urlAnhKhachSan='" + urlAnhKhachSan + '\'' +
                '}';
    }
}
