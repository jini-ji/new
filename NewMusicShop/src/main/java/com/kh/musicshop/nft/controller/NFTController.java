package com.kh.musicshop.nft.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.musicshop.nft.model.service.NFTService;
import com.kh.musicshop.nft.model.vo.NFT;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/nft")
public class NFTController {

    @Autowired
    private NFTService nftService;

    // ✅ 로그인 없이도 접근 가능 + DB 에러 시에도 페이지 출력
    @GetMapping("/my")
    public String myNFTList(HttpSession session, Model model) {
        List<NFT> myNFTs = new ArrayList<>();

        try {
            myNFTs = nftService.selectNFTsByUser("guest");
        } catch (Exception e) {
            System.out.println("[WARN] NFT 리스트 불러오기 실패: " + e.getMessage());
        }

        model.addAttribute("myNFTs", myNFTs);
        return "nft/nftList";
    }

    // ✅ 상세 정보 (DB 없을 경우 null 처리)
    @GetMapping("/detail")
    public String nftDetail(@RequestParam("nftId") String nftId, Model model) {
        NFT nft = null;

        try {
            nft = nftService.selectNFTById(nftId);
        } catch (Exception e) {
            System.out.println("[WARN] NFT 상세 조회 실패: " + e.getMessage());
        }

        model.addAttribute("nft", nft);
        return "nft/nftDetail";
    }

    // ✅ NFT 마켓 (DB 없어도 렌더링 됨)
    @GetMapping("/list")
    public String nftMarket(Model model) {
        List<NFT> nftList = new ArrayList<>();

        try {
            nftList = nftService.selectAllNFTs();
        } catch (Exception e) {
            System.out.println("[WARN] NFT 전체 리스트 조회 실패: " + e.getMessage());
        }

        model.addAttribute("nftList", nftList);
        return "nft/nftMarket";
    }
}