package model;

import java.sql.Timestamp;

public class TinNhan {
    private int id;
    private String nguoiGui;
    private String nguoiNhan;
    private String noiDung;
    private Timestamp thoiGian;

    // Constructors
    public TinNhan() {}

    public TinNhan(String nguoiGui, String nguoiNhan, String noiDung, Timestamp thoiGian) {
        this.nguoiGui = nguoiGui;
        this.nguoiNhan = nguoiNhan;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
    }

    // Getters và Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNguoiGui() { return nguoiGui; }
    public void setNguoiGui(String nguoiGui) { this.nguoiGui = nguoiGui; }

    public String getNguoiNhan() { return nguoiNhan; }
    public void setNguoiNhan(String nguoiNhan) { this.nguoiNhan = nguoiNhan; }

    public String getNoiDung() { return noiDung; }
    public void setNoiDung(String noiDung) { this.noiDung = noiDung; }

    public Timestamp getThoiGian() { return thoiGian; }
    public void setThoiGian(Timestamp thoiGian) { this.thoiGian = thoiGian; }
}
