package com.example.ck.item_class.userModel;

public class product_order {
    private String id;
    private String size;
    private Integer quantity;

    public product_order(String id, String size, Integer quantity) {
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
}
