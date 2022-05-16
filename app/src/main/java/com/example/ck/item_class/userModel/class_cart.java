package com.example.ck.item_class.userModel;

public class class_cart {
    public String id;
    public String size;
    public Integer quantity;

    public class_cart(String id, String size, Integer quantity) {
        this.id = id;
        this.size = size;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "class_cart{" +
                "id='" + id + '\'' +
                ", size='" + size + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
