package com.example.ck.item_class;

import com.example.ck.item_class.productModel.class_product;

import java.io.Serializable;
import java.util.ArrayList;

public class cart_item implements Serializable {
    private String id;
    private String name;
    private Integer quantity;
    private String size;
    private Integer price;
    private String link_image;


    public cart_item(String id, String name, Integer quantity, String size, Integer price, String link_image) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.size = size;
        this.price = price;
        this.link_image = link_image;
    }

    @Override
    public String toString() {
        return "cart_item{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", size='" + size + '\'' +
                ", price='" + price + '\'' +
                '}';
    }

    public String getLink_image() {
        return link_image;
    }

    public void setLink_image(String link_image) {
        this.link_image = link_image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


}
