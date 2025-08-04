package com.kh.musicshop.purchase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.musicshop.music.service.MusicService;
import com.kh.musicshop.music.vo.Music;
import com.kh.musicshop.purchase.dao.PurchaseDao;
import com.kh.musicshop.purchase.vo.Purchase;

import jakarta.servlet.http.HttpSession;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseDao purchaseDao;

    @Autowired
    private MusicService musicService;

    @Override
    public int purchaseMusic(Purchase p, HttpSession session) {
        int result = purchaseDao.insertPurchase(p);

        // VIP 여부 확인
        Music music = musicService.selectMusicById(p.getMusicNo());
        if ("Y".equals(music.getIsVipItem())) {
            session.setAttribute("isVIP", true);
        }

        return result;
    }

    @Override
    public List<Purchase> getUserPurchases(String userId) {
        return purchaseDao.getUserPurchases(userId);
    }

    @Override
    public boolean hasVipTrack(String userId) {
        System.out.println("🔥 [hasVipTrack] userId: " + userId);
        List<Purchase> userPurchases = purchaseDao.getUserPurchases(userId);
        System.out.println("🔥 [hasVipTrack] 구매 내역: " + userPurchases.size());

        for (Purchase p : userPurchases) {
            Music music = musicService.selectMusicById(p.getMusicNo());
            System.out.println("🔥 [hasVipTrack] 구매 음악: " + music.getTitle() + ", VIP 여부: " + music.getIsVipItem());
            if ("Y".equals(music.getIsVipItem())) {
                System.out.println("🔥 [hasVipTrack] VIP 트랙 확인됨!");
                return true;
            }
        }
        System.out.println("🔥 [hasVipTrack] VIP 트랙 없음!");
        return false;
    }
}