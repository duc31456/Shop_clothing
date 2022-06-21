package com.example.ck.item_class.userModel;

import android.text.TextUtils;
import android.util.Patterns;

import com.example.ck.item_class.userModel.class_account;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class class_user {
    public String id;
    public String name;
    public String phone;
    public String email;
    public String gender;
    public String age;
    public String address;
    public String linkAvt;
    public class_account account;
    public ArrayList<class_cart> cart;
    public ArrayList<class_order> orders;
    public String username;

    public class_user() {
    }

    public class_user(String id, String email, String linkAvt, class_account account) {
        this.id = id;
        this.email = email;
        this.linkAvt = linkAvt;
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLinkAvt() {
        return linkAvt;
    }

    public void setLinkAvt(String linkAvt) {
        this.linkAvt = linkAvt;
    }

    public class_account getAccount() {
        return account;
    }

    public void setAccount(class_account account) {
        this.account = account;
    }

    public ArrayList<class_cart> getCart() {
        return cart;
    }

    public void setCart(ArrayList<class_cart> cart) {
        this.cart = cart;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "class_user{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", linkAvt='" + linkAvt + '\'' +
                '}';
    }
}
