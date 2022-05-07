package com.example.ck.item_class.productModel;

public class class_stock {
    private String size;
    private Integer available;
    private Integer sold;

    public class_stock(String size, Integer available, Integer sold) {
        this.size = size;
        this.available = available;
        this.sold = sold;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getAvailable() {
        return available;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public Integer getSold() {
        return sold;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }

    @Override
    public String toString() {
        return "class_stock{" +
                "size='" + size + '\'' +
                ", available=" + available +
                ", sold=" + sold +
                '}';
    }
}
