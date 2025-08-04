package com.kh.musicshop.music.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.musicshop.music.vo.Music;

@Repository
public class MusicDao {

    @Autowired
    private SqlSession sqlSession;

    public Music selectMusicById(int musicNo) {
        System.out.println("🎯 [DAO] musicNo = " + musicNo);
        Music m = sqlSession.selectOne("musicMapper.selectMusicById", musicNo);
        System.out.println("🎯 [DAO] selectOne 결과: " + m);
        return m;
    }
}