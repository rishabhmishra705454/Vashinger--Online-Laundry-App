package com.rishabh.washer.model;

public class PromoCodeModel {

   private String title , description , minOrderPrice, discountPercentage , maxDiscountPrice , promoCode;

    public PromoCodeModel() {
    }

    public PromoCodeModel(String title, String description, String minOrderPrice, String discountPercentage, String maxDiscountPrice, String promoCode) {
        this.title = title;
        this.description = description;
        this.minOrderPrice = minOrderPrice;
        this.discountPercentage = discountPercentage;
        this.maxDiscountPrice = maxDiscountPrice;
        this.promoCode = promoCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMinOrderPrice() {
        return minOrderPrice;
    }

    public void setMinOrderPrice(String minOrderPrice) {
        this.minOrderPrice = minOrderPrice;
    }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public String getMaxDiscountPrice() {
        return maxDiscountPrice;
    }

    public void setMaxDiscountPrice(String maxDiscountPrice) {
        this.maxDiscountPrice = maxDiscountPrice;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }
}
