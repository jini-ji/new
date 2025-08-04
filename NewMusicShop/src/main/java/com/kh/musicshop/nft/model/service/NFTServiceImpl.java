package com.kh.musicshop.nft.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.musicshop.nft.model.dao.NFTDao;
import com.kh.musicshop.nft.model.vo.NFT;

@Service
public class NFTServiceImpl implements NFTService {

    @Autowired
    private NFTDao nftDao;

    @Override
    public List<NFT> selectNFTsByUser(String userId) {
        return nftDao.selectNFTsByUser(userId);
    }

    @Override
    public NFT selectNFTById(String nftId) {
        return nftDao.selectNFTById(nftId);
    }

    @Override
    public int insertNFTRecord(NFT nft) {
        return nftDao.insertNFTRecord(nft);
    }
    
    @Override
    public List<NFT> selectAllNFTs() {
        return nftDao.selectAllNFTs();
    }
}

