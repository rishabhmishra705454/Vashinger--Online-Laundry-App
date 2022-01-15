package com.rishabh.washer.model;

public class DisplayOrderModel {

    private String id ,uid , orderDate , orderTime , paymentType , status ,colorPreference , washingTemperature , additionalNote  , pickupDate, pickupTime, deliveryDate ,deliveryTime ,totalPrice , totalItem, serviceType ,address ,pincode,locality,latitude ,longitude ,phoneNo ,houseNo ,landmark , fullName ;

    private Boolean dryHeater , scentedDetergent  , useSoftner;

    public DisplayOrderModel() {
    }

    public DisplayOrderModel(String id, String uid, String orderDate, String orderTime, String paymentType, String status, String colorPreference, String washingTemperature, String additionalNote, String pickupDate, String pickupTime, String deliveryDate, String deliveryTime, String totalPrice, String totalItem, String serviceType, String address, String pincode, String locality, String latitude, String longitude, String phoneNo, String houseNo, String landmark, String fullName, Boolean dryHeater, Boolean scentedDetergent, Boolean useSoftner) {
        this.id = id;
        this.uid = uid;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.paymentType = paymentType;
        this.status = status;
        this.colorPreference = colorPreference;
        this.washingTemperature = washingTemperature;
        this.additionalNote = additionalNote;
        this.pickupDate = pickupDate;
        this.pickupTime = pickupTime;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
        this.totalPrice = totalPrice;
        this.totalItem = totalItem;
        this.serviceType = serviceType;
        this.address = address;
        this.pincode = pincode;
        this.locality = locality;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNo = phoneNo;
        this.houseNo = houseNo;
        this.landmark = landmark;
        this.fullName = fullName;
        this.dryHeater = dryHeater;
        this.scentedDetergent = scentedDetergent;
        this.useSoftner = useSoftner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColorPreference() {
        return colorPreference;
    }

    public void setColorPreference(String colorPreference) {
        this.colorPreference = colorPreference;
    }

    public String getWashingTemperature() {
        return washingTemperature;
    }

    public void setWashingTemperature(String washingTemperature) {
        this.washingTemperature = washingTemperature;
    }

    public String getAdditionalNote() {
        return additionalNote;
    }

    public void setAdditionalNote(String additionalNote) {
        this.additionalNote = additionalNote;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(String totalItem) {
        this.totalItem = totalItem;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Boolean getDryHeater() {
        return dryHeater;
    }

    public void setDryHeater(Boolean dryHeater) {
        this.dryHeater = dryHeater;
    }

    public Boolean getScentedDetergent() {
        return scentedDetergent;
    }

    public void setScentedDetergent(Boolean scentedDetergent) {
        this.scentedDetergent = scentedDetergent;
    }

    public Boolean getUseSoftner() {
        return useSoftner;
    }

    public void setUseSoftner(Boolean useSoftner) {
        this.useSoftner = useSoftner;
    }
}
