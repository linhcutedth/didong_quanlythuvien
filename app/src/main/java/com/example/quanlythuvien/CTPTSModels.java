package com.example.quanlythuvien;

public class CTPTSModels {
    private int Ma_Pts;
    private int MaSach ;
    private String TenSach;
    private int SoNgayTraTre;
    private int TienPhat;

    public CTPTSModels() {
    }

    public CTPTSModels(int ma_Pts, int maSach, String tenSach, int soNgayTraTre, int tienPhat) {
        Ma_Pts = ma_Pts;
        MaSach = maSach;
        TenSach = tenSach;
        SoNgayTraTre = soNgayTraTre;
        TienPhat = tienPhat;
    }

    public int getMa_Pts() {
        return Ma_Pts;
    }

    public void setMa_Pts(int ma_Pts) {
        Ma_Pts = ma_Pts;
    }

    public int getMaSach() {
        return MaSach;
    }

    public void setMaSach(int maSach) {
        MaSach = maSach;
    }

    public String getTenSach() {
        return TenSach;
    }

    public void setTenSach(String tenSach) {
        TenSach = tenSach;
    }

    public int getSoNgayTraTre() {
        return SoNgayTraTre;
    }

    public void setSoNgayTraTre(int soNgayTraTre) {
        SoNgayTraTre = soNgayTraTre;
    }

    public int getTienPhat() {
        return TienPhat;
    }

    public void setTienPhat(int tienPhat) {
        TienPhat = tienPhat;
    }
}
