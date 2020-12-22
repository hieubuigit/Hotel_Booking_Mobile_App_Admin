package com.chuyende.hotelbookingappofadmin.data_model;

public class ThongKeDoanhThuKS {
    String maThongKeDTKS, maPhong, thangDoanhThu;
    int doanhThu;

    public ThongKeDoanhThuKS(String maThongKeDTKS, String maPhong, String thangDoanhThu, int doanhThu) {
        this.maThongKeDTKS = maThongKeDTKS;
        this.maPhong = maPhong;
        this.thangDoanhThu = thangDoanhThu;
        this.doanhThu = doanhThu;
    }

    public String getMaThongKeDTKS() {
        return maThongKeDTKS;
    }

    public void setMaThongKeDTKS(String maThongKeDTKS) {
        this.maThongKeDTKS = maThongKeDTKS;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getThangDoanhThu() {
        return thangDoanhThu;
    }

    public void setThangDoanhThu(String thangDoanhThu) {
        this.thangDoanhThu = thangDoanhThu;
    }

    public int getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(int doanhThu) {
        this.doanhThu = doanhThu;
    }
}
