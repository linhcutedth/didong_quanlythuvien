package com.example.quanlythuvien;

public class PhieuThuModels {
    private int MaPhieuThu;
    private int Ma_PTS;
    private int TienNo;
    private int TienThu;

    public PhieuThuModels() {
    }

    public PhieuThuModels(int maPhieuThu, int ma_PTS, int tienNo, int tienThu) {
        MaPhieuThu = maPhieuThu;
        Ma_PTS = ma_PTS;
        TienNo = tienNo;
        TienThu = tienThu;
    }

    public int getMaPhieuThu() {
        return MaPhieuThu;
    }

    public void setMaPhieuThu(int maPhieuThu) {
        MaPhieuThu = maPhieuThu;
    }

    public int getMa_PTS() {
        return Ma_PTS;
    }

    public void setMa_PTS(int ma_PTS) {
        Ma_PTS = ma_PTS;
    }

    public int getTienNo() {
        return TienNo;
    }

    public void setTienNo(int tienNo) {
        TienNo = tienNo;
    }

    public int getTienThu() {
        return TienThu;
    }

    public void setTienThu(int tienThu) {
        TienThu = tienThu;
    }
}
