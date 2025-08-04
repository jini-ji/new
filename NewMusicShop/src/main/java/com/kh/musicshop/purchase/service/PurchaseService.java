package com.kh.musicshop.purchase.service;

import java.util.List;

import com.kh.musicshop.purchase.vo.Purchase;

import jakarta.servlet.http.HttpSession;

public interface PurchaseService {
    boolean hasVipTrack(String userId);
    int purchaseMusic(Purchase p, HttpSession session);
    List<Purchase> getUserPurchases(String userId);
}