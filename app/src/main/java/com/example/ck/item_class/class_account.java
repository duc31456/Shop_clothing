package com.example.ck.item_class;

public class class_account {
    private String username;
    private String password;
    private String role;
    private String created_at;

    public class_account(String username, String password, String role, String created_at) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.created_at = created_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
