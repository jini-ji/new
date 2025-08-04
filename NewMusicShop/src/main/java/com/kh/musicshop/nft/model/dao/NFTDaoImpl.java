package com.kh.musicshop.nft.model.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.musicshop.nft.model.vo.NFT;

@Repository
public class NFTDaoImpl implements NFTDao {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public List<NFT> selectNFTsByUser(String userId) {
        return sqlSession.selectList("nftMapper.selectNFTsByUser", userId);
    }

    @Override
    public NFT selectNFTById(String nftId) {
        return sqlSession.selectOne("nftMapper.selectNFTById", nftId);
    }

    @Override
    public int insertNFTRecord(NFT nft) {
        return sqlSession.insert("nftMapper.insertNFTRecord", nft);
    }

	
	// ✅ 이거 없어서 지금 서버가 뻗은 거야
    @Override
    public List<NFT> selectAllNFTs() {
        return sqlSession.selectList("nftMapper.selectAllNFTs");
    }
}