package com.kh.musicshop.music.vo;

public class Music {
    private int musicNo;
    private String title;
    private String artist;
    private int price;
    private String samplePath;
    private String imagePath;
    private String isVipItem; // VIP 여부 (Y/N)

    // Getter & Setter
    public int getMusicNo() {
        return musicNo;
    }

    public void setMusicNo(int musicNo) {
        this.musicNo = musicNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSamplePath() {
        return samplePath;
    }

    public void setSamplePath(String samplePath) {
        this.samplePath = samplePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getIsVipItem() {
        return isVipItem;
    }

    public void setIsVipItem(String isVipItem) {
        this.isVipItem = isVipItem;
    }
}