package com.chuyende.hotelbookingappofadmin.data_model;

public class TinhThanhPho {
    private String maTinhThanhPho;
    private String tinhThanhPho;

    public TinhThanhPho() {
    }

    public TinhThanhPho(String maTinhThanhPho, String tinhThanhPho) {
        this.maTinhThanhPho = maTinhThanhPho;
        this.tinhThanhPho = tinhThanhPho;
    }

    public String getMaTinhThanhPho() {
        return maTinhThanhPho;
    }

    public void setMaTinhThanhPho(String maTinhThanhPho) {
        this.maTinhThanhPho = maTinhThanhPho;
    }

    public String getTinhThanhPho() {
        return tinhThanhPho;
    }

    public void setTinhThanhPho(String tinhThanhPho) {
        this.tinhThanhPho = tinhThanhPho;
    }

    @Override
    public String toString() {
        return "TinhThanhPho{" +
                "maTinhThanhPho='" + maTinhThanhPho + '\'' +
                "\n -- tinhThanhPho='" + tinhThanhPho + '\'' +
                '}';
    }
}
