package com.example.quanlythuvien;

public class DauSachModels {
    private int MA_DAUSACH;
    private String TENDAUSACH;
    private String TACGIA;
    private String NXB;
    private int NAMXB;
    private int TONGSO;
    private String VITRI;
    private int SANCO;
    private int DANGCHOMUON;
    private String THELOAI;
    private String HINHANH;

    public DauSachModels() {
    }

    public DauSachModels(int MA_DAUSACH, String TENDAUSACH, String TACGIA, String NXB, int NAMXB, int TONGSO, String VITRI, int SANCO, int DANGCHOMUON, String THELOAI, String HINHANH) {
        this.MA_DAUSACH = MA_DAUSACH;
        this.TENDAUSACH = TENDAUSACH;
        this.TACGIA = TACGIA;
        this.NXB = NXB;
        this.NAMXB = NAMXB;
        this.TONGSO = TONGSO;
        this.VITRI = VITRI;
        this.SANCO = SANCO;
        this.DANGCHOMUON = DANGCHOMUON;
        this.THELOAI = THELOAI;
        this.HINHANH = HINHANH;
    }

    public int getMA_DAUSACH() {
        return MA_DAUSACH;
    }

    public void setMA_DAUSACH(int MA_DAUSACH) {
        this.MA_DAUSACH = MA_DAUSACH;
    }

    public String getTENDAUSACH() {
        return TENDAUSACH;
    }

    public void setTENDAUSACH(String TENDAUSACH) {
        this.TENDAUSACH = TENDAUSACH;
    }

    public String getTACGIA() {
        return TACGIA;
    }

    public void setTACGIA(String TACGIA) {
        this.TACGIA = TACGIA;
    }

    public String getNXB() {
        return NXB;
    }

    public void setNXB(String NXB) {
        this.NXB = NXB;
    }

    public int getNAMXB() {
        return NAMXB;
    }

    public void setNAMXB(int NAMXB) {
        this.NAMXB = NAMXB;
    }

    public int getTONGSO() {
        return TONGSO;
    }

    public void setTONGSO(int TONGSO) {
        this.TONGSO = TONGSO;
    }

    public String getVITRI() {
        return VITRI;
    }

    public void setVITRI(String VITRI) {
        this.VITRI = VITRI;
    }

    public int getSANCO() {
        return SANCO;
    }

    public void setSANCO(int SANCO) {
        this.SANCO = SANCO;
    }

    public int getDANGCHOMUON() {
        return DANGCHOMUON;
    }

    public void setDANGCHOMUON(int DANGCHOMUON) {
        this.DANGCHOMUON = DANGCHOMUON;
    }

    public String getTHELOAI() {
        return THELOAI;
    }

    public void setTHELOAI(String THELOAI) {
        this.THELOAI = THELOAI;
    }

    public String getHINHANH() {
        return HINHANH;
    }

    public void setHINHANH(String HINHANH) {
        this.HINHANH = HINHANH;
    }
}
