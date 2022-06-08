package com.example.quanlythuvien;

public class PhieuTraModels {
    private int Ma_PTS;
    private int Ma_DG;
    private String NgayTra;
    private int TienPhatKyNay;

    public PhieuTraModels(int ma_PTS, int ma_DG, String ngayTra, int tienPhatKyNay) {
        Ma_PTS = ma_PTS;
        Ma_DG = ma_DG;
        NgayTra = ngayTra;
        TienPhatKyNay = tienPhatKyNay;
    }

    public PhieuTraModels() {
    }

    public int getMa_PTS() {
        return Ma_PTS;
    }

    public void setMa_PTS(int ma_PTS) {
        Ma_PTS = ma_PTS;
    }

    public int getMa_DG() {
        return Ma_DG;
    }

    public void setMa_DG(int ma_DG) {
        Ma_DG = ma_DG;
    }

    public String getNgayTra() {
        return NgayTra;
    }

    public void setNgayTra(String ngayTra) {
        NgayTra = ngayTra;
    }

    public int getTienPhatKyNay() {
        return TienPhatKyNay;
    }

    public void setTienPhatKyNay(int tienPhatKyNay) {
        TienPhatKyNay = tienPhatKyNay;
    }


}
