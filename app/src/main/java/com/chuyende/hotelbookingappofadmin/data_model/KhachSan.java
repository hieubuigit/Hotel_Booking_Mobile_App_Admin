package com.chuyende.hotelbookingappofadmin.data_model;

import java.util.Arrays;

public class KhachSan {
    String maKhachSan, tenKhachSan, diaDiemKhachSan, anhDaiDienKS, tenTaiKhoanKhachSan;

    public KhachSan() {
    }

    public KhachSan(String maKhachSan, String tenKhachSan, String diaDiemKhachSan, String anhDaiDienKS, String tenTaiKhoanKhachSan) {
        this.maKhachSan = maKhachSan;
        this.tenKhachSan = tenKhachSan;
        this.diaDiemKhachSan = diaDiemKhachSan;
        this.anhDaiDienKS = anhDaiDienKS;
        this.tenTaiKhoanKhachSan = tenTaiKhoanKhachSan;
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

    public String getDiaDiemKhachSan() {
        return diaDiemKhachSan;
    }

    public void setDiaDiemKhachSan(String diaDiemKhachSan) {
        this.diaDiemKhachSan = diaDiemKhachSan;
    }

    public String getAnhDaiDienKS() {
        return anhDaiDienKS;
    }

    public void setAnhDaiDienKS(String anhDaiDienKS) {
        this.anhDaiDienKS = anhDaiDienKS;
    }

    public String getTenTaiKhoanKhachSan() {
        return tenTaiKhoanKhachSan;
    }

    public void setTenTaiKhoanKhachSan(String tenTaiKhoanKhachSan) {
        this.tenTaiKhoanKhachSan = tenTaiKhoanKhachSan;
    }
}
