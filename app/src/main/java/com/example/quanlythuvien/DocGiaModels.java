package com.example.quanlythuvien;

public class DocGiaModels {
    private int MaDG;
    private String HoTen;
    private String NgSinh;
    private String LoaiDG;
    private String DiaChi;
    private String Email;
    private String NgLapThe;
    private String TinhTrangThe;

    public DocGiaModels(int maDG, String hoTen, String ngSinh, String loaiDG, String diaChi, String email, String ngLapThe, String tinhTrangThe) {
        MaDG = maDG;
        HoTen = hoTen;
        NgSinh = ngSinh;
        LoaiDG = loaiDG;
        DiaChi = diaChi;
        Email = email;
        NgLapThe = ngLapThe;
        TinhTrangThe = tinhTrangThe;
    }

    public int getMaDG() {
        return MaDG;
    }

    public void setMaDG(int maDG) {
        MaDG = maDG;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getNgSinh() {
        return NgSinh;
    }

    public void setNgSinh(String ngSinh) {
        NgSinh = ngSinh;
    }

    public String getLoaiDG() {
        return LoaiDG;
    }

    public void setLoaiDG(String loaiDG) {
        LoaiDG = loaiDG;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNgLapThe() {
        return NgLapThe;
    }

    public void setNgLapThe(String ngLapThe) {
        NgLapThe = ngLapThe;
    }

    public String getTinhTrangThe() {
        return TinhTrangThe;
    }

    public void setTinhTrangThe(String tinhTrangThe) {
        TinhTrangThe = tinhTrangThe;
    }
}
