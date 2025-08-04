package com.kh.musicshop.music.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.musicshop.member.model.vo.Member;
import com.kh.musicshop.purchase.service.PurchaseService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MusicController {

    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping("list.mu")
    public String showMusicList(HttpSession session, Model model) {
        Member loginUser = (Member) session.getAttribute("loginUser");

        // ✅ loginUser 객체를 model에 추가 (Thymeleaf에서 isLoggedIn 판단용)
        model.addAttribute("loginUser", loginUser);

        if (loginUser != null) {
            boolean hasVipTrack = purchaseService.hasVipTrack(loginUser.getUserId());
            session.setAttribute("isVIP", hasVipTrack);
        }

        return "music/musicList"; // => templates/music/musicList.html
    }

    @RequestMapping("purchase.mu")
    public String purchaseMusic() {
        return "redirect:/list.mu";
    }

    @RequestMapping("vipPage")
    public String accessVipPage(HttpSession session) {
        Member loginUser = (Member) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/login.me";
        }

        boolean hasVipTrack = purchaseService.hasVipTrack(loginUser.getUserId());
        if (!hasVipTrack) {
            return "redirect:/accessDenied";
        }

        return "vip/vipPage";
    }
}