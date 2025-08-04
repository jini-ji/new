package com.kh.musicshop.nft.model.dao;

import java.util.List;

import com.kh.musicshop.nft.model.vo.NFT;


public interface NFTDao {
    List<NFT> selectNFTsByUser(String userId);
    NFT selectNFTById(String nftId);
    int insertNFTRecord(NFT nft);
	List<NFT> selectAllNFTs();
}

