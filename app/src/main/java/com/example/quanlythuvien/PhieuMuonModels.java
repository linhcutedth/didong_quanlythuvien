package com.example.quanlythuvien;

public class PhieuMuonModels {
    private int Ma_PMS;
    private int Ma_DG;
    private String NgayMuon;

    public PhieuMuonModels(int ma_PMS, int ma_DG, String ngayMuon) {
        Ma_PMS = ma_PMS;
        Ma_DG = ma_DG;
        NgayMuon = ngayMuon;
    }

    public int getMa_PMS() {
        return Ma_PMS;
    }

    public void setMa_PMS(int ma_PMS) {
        Ma_PMS = ma_PMS;
    }

    public int getMa_DG() {
        return Ma_DG;
    }

    public void setMa_DG(int ma_DG) {
        Ma_DG = ma_DG;
    }

    public String getNgayMuon() {
        return NgayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        NgayMuon = ngayMuon;
    }
}
