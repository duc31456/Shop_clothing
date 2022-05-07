package com.example.ck.item_class.productModel;

public class class_review {
    private String id_user;
    private String rate;
    private String feedback;
    private String created_at;

    public class_review(String id_user, String rate, String feedback, String created_at) {
        this.id_user = id_user;
        this.rate = rate;
        this.feedback = feedback;
        this.created_at = created_at;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
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
                "id_user='" + id_user + '\'' +
                ", rate='" + rate + '\'' +
                ", feedback='" + feedback + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
