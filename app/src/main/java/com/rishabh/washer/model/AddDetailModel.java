package com.rishabh.washer.model;

public class AddDetailModel {
    private String image ;
    private String title ;
    private String price ;
    private String quantity ;
    private String status;

    public AddDetailModel() {
    }

    public AddDetailModel(String image, String title, String price, String quantity, String status) {
        this.image = image;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
