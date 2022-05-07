package com.example.ck.item_class.userModel;

import com.example.ck.item_class.userModel.class_account;

public class class_user {
    private String id;
    private String name;
    private String phone;
    private String gender;
    private String age;
    private String address;
    private String link_avt;
    private class_account account;

    public class_user(String id, String name, String phone, String gender, String age, String address, String link_avt, class_account account) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.age = age;
        this.address = address;
        this.link_avt = link_avt;
        this.account = account;
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

    public String getLink_avt() {
        return link_avt;
    }

    public void setLink_avt(String link_avt) {
        this.link_avt = link_avt;
    }

    public class_account getAccount() {
        return account;
    }

    public void setAccount(class_account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "class_user{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", gender='" + gender + '\'' +
                ", age='" + age + '\'' +
                ", address='" + address + '\'' +
                ", link_avt='" + link_avt + '\'' +
                ", account=" + account +
                '}';
    }
}
