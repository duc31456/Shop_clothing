package com.example.ck.item_class.userModel;

import com.example.ck.item_class.productModel.class_product;

import java.util.ArrayList;

public class class_order {
    private String id;
    private String recipient_name;
    private String recipient_phone;
    private String recipient_email;
    private String recipient_address;
    private ArrayList<product_order> products;
    private ArrayList<class_product> product;
    private Integer discount;
    private Integer total_price;
    private String status;

    public class_order(String id, String recipient_name, String recipient_phone,
                       String recipient_email, String recipient_address,
                       ArrayList<product_order> products,
                       Integer discount, Integer total_price, String status,ArrayList<class_product> product) {
        this.id = id;
        this.recipient_name = recipient_name;
        this.recipient_phone = recipient_phone;
        this.recipient_email = recipient_email;
        this.recipient_address = recipient_address;
        this.products = products;
        this.discount = discount;
        this.total_price = total_price;
        this.status = status;
        this.product = product;
    }

    public ArrayList<class_product> getProduct() {
        return product;
    }

    public void setProduct(ArrayList<class_product> product) {
        this.product = product;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRecipient_name() {
        return recipient_name;
    }

    public void setRecipient_name(String recipient_name) {
        this.recipient_name = recipient_name;
    }

    public String getRecipient_phone() {
        return recipient_phone;
    }

    public void setRecipient_phone(String recipient_phone) {
        this.recipient_phone = recipient_phone;
    }

    public String getRecipient_email() {
        return recipient_email;
    }

    public void setRecipient_email(String recipient_email) {
        this.recipient_email = recipient_email;
    }

    public String getRecipient_address() {
        return recipient_address;
    }

    public void setRecipient_address(String recipient_address) {
        this.recipient_address = recipient_address;
    }

    public ArrayList<product_order> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<product_order> products) {
        this.products = products;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Integer total_price) {
        this.total_price = total_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return "class_order{" +
                "id='" + id + '\'' +
                ", recipient_name='" + recipient_name + '\'' +
                ", recipient_phone='" + recipient_phone + '\'' +
                ", recipient_email='" + recipient_email + '\'' +
                ", recipient_address='" + recipient_address + '\'' +
                ", products=" + products +
                ", discount=" + discount +
                ", total_price=" + total_price +
                ", status='" + status + '\'' +
                '}';
    }
}
