package com.chuyende.hotelbookingappofadmin.data_model;

import com.google.firebase.firestore.Exclude;

public class NguoiDung {
    @Exclude String id;
    String tenNguoiDung, gioiTinh, ngaySinh, quocTich, diaChi, email, soDienThoai, anhDaiDien, cmnd;

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getQuocTich() {
        return quocTich;
    }

    public void setQuocTich(String quocTich) {
        this.quocTich = quocTich;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDT() {
        return soDienThoai;
    }

    public void setSoDT(String soDT) {
        this.soDienThoai = soDT;
    }

    public String getUrlAnhDaiDien() {
        return anhDaiDien;
    }

    public void setUrlAnhDaiDien(String urlAnhDaiDien) {
        this.anhDaiDien = urlAnhDaiDien;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    @Override
    public String toString() {
        return "NguoiDung{" +
                "id='" + id + '\'' +
                ", tenNguoiDung='" + tenNguoiDung + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", ngaySinh='" + ngaySinh + '\'' +
                ", quocTich='" + quocTich + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", email='" + email + '\'' +
                ", soDT='" + soDienThoai + '\'' +
                ", urlAnhDaiDien='" + anhDaiDien + '\'' +
                ", cmnd='" + cmnd + '\'' +
                '}';
    }
}
