package com.example.ck.item_class;

public class class_receipt {
    private String ten;
    private String gia;
    private String image;
    private String mota;

    public class_receipt(String ten, String gia, String image, String mota) {
        this.ten = ten;
        this.gia = gia;
        this.image = image;
        this.mota = mota;
    }

    public class_receipt() {
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
