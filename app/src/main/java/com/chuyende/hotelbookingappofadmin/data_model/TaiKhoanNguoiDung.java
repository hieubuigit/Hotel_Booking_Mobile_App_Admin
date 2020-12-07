package com.chuyende.hotelbookingappofadmin.data_model;

import com.google.firebase.firestore.Exclude;

public class TaiKhoanNguoiDung {
    @Exclude String id;
    String tenTaiKhoan, matKhau, email, soDienThoai, trangThaiTaiKhoan;

    public TaiKhoanNguoiDung(String id, String tenTaiKhoan, String matKhau, String email, String soDienThoai, String trangThaiTaiKhoan) {
        this.id = id;
        this.tenTaiKhoan = tenTaiKhoan;
        this.matKhau = matKhau;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.trangThaiTaiKhoan = trangThaiTaiKhoan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getTrangThaiTaiKhoan() {
        return trangThaiTaiKhoan;
    }

    public void setTrangThaiTaiKhoan(String trangThaiTaiKhoan) {
        this.trangThaiTaiKhoan = trangThaiTaiKhoan;
    }

    @Override
    public String toString() {
        return "TaiKhoanNguoiDung{" +
                "id='" + id + '\'' +
                ", tenTaiKhoan='" + tenTaiKhoan + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", email='" + email + '\'' +
                ", soDienThoai='" + soDienThoai + '\'' +
                ", trangThaiTaiKhoan='" + trangThaiTaiKhoan + '\'' +
                '}';
    }
}
