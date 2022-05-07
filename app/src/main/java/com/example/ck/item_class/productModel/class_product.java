package com.example.ck.item_class.productModel;

import java.util.ArrayList;

public class class_product {
    private String id;
    private String name;
    private String category;
    private int price;
    private String description;
    private String created_at;
    private int discount;
    private String link_image;
    private ArrayList<class_review> review;
    private ArrayList<class_stock> stock;


    public class_product(String id, String name, String category, int price, String description, String created_at, int discount, String link_image, ArrayList<class_review> review, ArrayList<class_stock> stock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
        this.created_at = created_at;
        this.discount = discount;
        this.link_image = link_image;
        this.review = review;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "class_product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", created_at='" + created_at + '\'' +
                ", discount=" + discount +
                ", link_image='" + link_image + '\'' +
                ", review=" + review.toString() +
                ", stock=" + stock.toString() +
                '}';
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getLink_image() {
        return link_image;
    }

    public void setLink_image(String link_image) {
        this.link_image = link_image;
    }

    public ArrayList<class_review> getReview() {
        return review;
    }

    public void setReview(ArrayList<class_review> review) {
        this.review = review;
    }

    public ArrayList<class_stock> getStock() {
        return stock;
    }

    public void setStock(ArrayList<class_stock> stock) {
        this.stock = stock;
    }
}
