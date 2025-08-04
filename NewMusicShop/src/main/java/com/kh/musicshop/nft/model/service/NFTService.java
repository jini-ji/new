package com.kh.musicshop.nft.model.service;

import java.util.List;

import com.kh.musicshop.nft.model.vo.NFT;


public interface NFTService {
    List<NFT> selectNFTsByUser(String userId);
    NFT selectNFTById(String nftId);
    int insertNFTRecord(NFT nft);
    
    List<NFT> selectAllNFTs();
	
}
