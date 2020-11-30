package com.chuyende.hotelbookingappofadmin.data_model;

public class NguoiDung {
    String maNguoiDung, tenNguoiDung, gioiTinh, ngaySinh, quocTich, diaChi, email, soDT, urlAnhDaiDien, cmnd;

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
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
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getUrlAnhDaiDien() {
        return urlAnhDaiDien;
    }

    public void setUrlAnhDaiDien(String urlAnhDaiDien) {
        this.urlAnhDaiDien = urlAnhDaiDien;
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
                "maNguoiDung='" + maNguoiDung + '\'' +
                ", tenNguoiDung='" + tenNguoiDung + '\'' +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", ngaySinh='" + ngaySinh + '\'' +
                ", quocTich='" + quocTich + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", email='" + email + '\'' +
                ", soDT='" + soDT + '\'' +
                ", urlAnhDaiDien='" + urlAnhDaiDien + '\'' +
                ", cmnd='" + cmnd + '\'' +
                '}';
    }
}
