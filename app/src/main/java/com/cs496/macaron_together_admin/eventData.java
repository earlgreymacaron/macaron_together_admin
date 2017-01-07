package com.cs496.macaron_together_admin;

import java.util.List;

/**
 * Created by q on 2017-01-06.
 */

public class EventData {
    private String shop_name;
    private String shop_addr;
    private String start_date;
    private String end_date;
    private String price;
    private List<String> flavors;
    private String photo;
    private String status;

    public EventData() { }

    public EventData(String shop_name, String shop_addr, String start_date, String end_date,
                     String price, List<String> flavors, String photo, String status) {
        this.shop_name = shop_name;
        this.shop_addr = shop_addr;
        this.start_date = start_date;
        this.end_date = end_date;
        this.price = price;
        this.flavors = flavors;
        this.photo = photo;
        this.status = status;
    }

    public String getShopName() {
        return shop_name;
    }

    public void setShopName(String name) {
        shop_name = name;
    }

    public String getShopAddr() {
        return shop_addr;
    }

    public void setShopAddr(String addr) {
        shop_addr = addr;
    }

    public String getStartDate() {
        return start_date;
    }

    public void setStartDate(String date) {
        start_date = date;
    }

    public String getEndDate() {
        return end_date;
    }

    public void setEndDate(String date) {
        end_date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String p) {
        price = p;
    }

    public List<String> getFlavors() {
        return flavors;
    }

    public void setFlavors(List<String> f) { flavors = f; }

    public String getPhotos() {
        return photo;
    }

    public void setPhotos(String p) { photo = p; }

    public String setStatus() { return status; }

    public void setStatus(String s) { status = s; }

}
