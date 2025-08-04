package com.kh.musicshop.purchase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.musicshop.member.model.vo.Member;
import com.kh.musicshop.music.service.MusicService;
import com.kh.musicshop.music.vo.Music;
import com.kh.musicshop.purchase.service.PurchaseService;
import com.kh.musicshop.purchase.vo.Purchase;

import jakarta.servlet.http.HttpSession;

@Controller
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;
    
    @Autowired
    private MusicService musicService;
    
    

    /**
     * 음원 구매 처리
     */
    @PostMapping("purchase.mu")
    public String purchaseMusic(
            @RequestParam("musicNo") int musicNo,
            HttpSession session) {

        // 로그인 확인
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            session.setAttribute("alertMsg", "로그인 후 이용해주세요.");
            return "redirect:/list.mu"; // 리스트로 돌려보내야 alert 스크립트가 동작
        }

        // 트랙 정보 조회
        Music track = musicService.selectMusicById(musicNo);
        if (track == null) {
            session.setAttribute("alertMsg", "존재하지 않는 트랙입니다.");
            return "redirect:/list.mu";
        }

        // 구매 VO 세팅
        Purchase p = new Purchase();
        p.setUserId(loginUser.getUserId());
        p.setMusicNo(musicNo);
        p.setPrice(track.getPrice());

        // 구매 실행 (세션도 전달)
        int result = purchaseService.purchaseMusic(p, session);
        if (result > 0) {
            // 구매 완료는 세션 alert 없이 전용 뷰로
            return "redirect:/purchaseComplete.mu";
        } else {
            session.setAttribute("alertMsg", "구매 실패, 다시 시도해주세요.");
            return "redirect:/list.mu";
        }
    }
    
    
    @GetMapping("/pay/success")
    public String paySuccess(@RequestParam("musicNo") int musicNo,
                             HttpSession session) {

        System.out.println("🎯 [paySuccess] 전달받은 musicNo: " + musicNo);

        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            session.setAttribute("alertMsg", "세션 만료. 다시 로그인해주세요.");
            return "redirect:/login";
        }

        Music music = musicService.selectMusicById(musicNo);
        if (music == null) {
            System.out.println("🚨 음악 없음");
            session.setAttribute("alertMsg", "존재하지 않는 트랙입니다.");
            return "redirect:/list.mu";
        }

        Purchase p = new Purchase();
        p.setUserId(loginUser.getUserId());
        p.setMusicNo(musicNo);
        p.setPrice(music.getPrice());

        int result = purchaseService.purchaseMusic(p, session);
        System.out.println("🔥 구매 저장 결과: " + result);

        // VIP 여부 판단 후 세션 저장
        if ("Y".equals(music.getIsVipItem())) {
            session.setAttribute("isVIP", true);
        } else {
            session.removeAttribute("isVIP");
        }

        // 🎯 완료 페이지로 이동
        return "redirect:/purchaseComplete.mu";
    }
    

    /**
     * 내 구매내역 조회
     */
    @GetMapping("purchaseList.mu")
    public String showPurchases(HttpSession session, Model model) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            session.setAttribute("alertMsg", "로그인 후 이용해주세요.");
            return "redirect:/list.mu";
        }

        List<Purchase> list = purchaseService.getUserPurchases(loginUser.getUserId());
        model.addAttribute("list", list);
        return "music/purchaseListView";
    }

    /**
     * 구매 완료 안내 페이지
     */
    @GetMapping("purchaseComplete.mu")
    public String purchaseComplete() {
        return "purchase/purchaseComplete";
    }
}