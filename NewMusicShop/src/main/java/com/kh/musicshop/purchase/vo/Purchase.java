package com.kh.musicshop.purchase.vo;

import java.util.Date;

public class Purchase {
    private int     purchaseNo;
    private String  userId;
    private int     musicNo;
    private Date    purchaseDate;
    private int     price;

    // -- getters & setters --
    public int getPurchaseNo() { return purchaseNo; }
    public void setPurchaseNo(int purchaseNo) { this.purchaseNo = purchaseNo; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public int getMusicNo() { return musicNo; }
    public void setMusicNo(int musicNo) { this.musicNo = musicNo; }
    public Date getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(Date purchaseDate) { this.purchaseDate = purchaseDate; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
}
