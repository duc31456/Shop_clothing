package com.example.ck.item_class.productModel;

public class class_review {
    private String username;
    private Integer rate;
    private String feedback;
    private String created_at;

    public class_review(String username, Integer rate, String feedback, String created_at) {
        this.username = username;
        this.rate = rate;
        this.feedback = feedback;
        this.created_at = created_at;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "class_review{" +
                "username='" + username + '\'' +
                ", rate=" + rate +
                ", feedback='" + feedback + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
