package com.example.quanlythuvien;

public class ChiTietMuonSachModels {

    private int Ma_pms;
    private int Ma_sach;
    private String Ten_sach;
    private String Tinh_trang;

    public ChiTietMuonSachModels() {
    }

    public ChiTietMuonSachModels(int ma_pms, int ma_sach, String ten_sach, String tinh_trang) {
        Ma_pms = ma_pms;
        Ma_sach = ma_sach;
        Ten_sach = ten_sach;
        Tinh_trang = tinh_trang;
    }

    public int getMa_pms() {
        return Ma_pms;
    }

    public void setMa_pms(int ma_pms) {
        Ma_pms = ma_pms;
    }

    public int getMa_sach() {
        return Ma_sach;
    }

    public void setMa_sach(int ma_sach) {
        Ma_sach = ma_sach;
    }

    public String getTen_sach() {
        return Ten_sach;
    }

    public void setTen_sach(String ten_sach) {
        Ten_sach = ten_sach;
    }

    public String getTinh_trang() {
        return Tinh_trang;
    }

    public void setTinh_trang(String tinh_trang) {
        Tinh_trang = tinh_trang;
    }
}
