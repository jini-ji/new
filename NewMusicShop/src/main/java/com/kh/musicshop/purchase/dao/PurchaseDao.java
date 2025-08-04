package com.kh.musicshop.purchase.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.musicshop.purchase.vo.Purchase;

@Repository
public class PurchaseDao {

    @Autowired
    private SqlSession sqlSession;

    public int insertPurchase(Purchase p) {
        return sqlSession.insert("purchaseMapper.insertPurchase", p);
    }

    public List<Purchase> getUserPurchases(String userId) {
        return sqlSession.selectList("purchaseMapper.selectUserPurchases", userId);
    }
}