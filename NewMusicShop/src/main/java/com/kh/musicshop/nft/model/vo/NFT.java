package com.kh.musicshop.nft.model.vo;

import java.util.Date;

public class NFT {
    private String nftId;
    private String userId;
    private int musicNo;
    private String mintHash;
    private Date mintDate;

    // ✅ Getter & Setter

    public int getMusicNo() {
        return musicNo;
    }

    public void setMusicNo(int musicNo) {
        this.musicNo = musicNo;
    }

    public String getMintHash() {
        return mintHash;
    }

    public void setMintHash(String mintHash) {
        this.mintHash = mintHash;
    }

    public Date getMintDate() {
        return mintDate;
    }

    public void setMintDate(Date mintDate) {
        this.mintDate = mintDate;
    }

    // (필요시 나머지도 추가 가능)
}