package com.kh.musicshop.music.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.musicshop.music.dao.MusicDao;
import com.kh.musicshop.music.vo.Music;

@Service
public class MusicServiceImpl implements MusicService {

    @Autowired
    private MusicDao musicDao;

    @Override
    public Music selectMusicById(int musicNo) {
        return musicDao.selectMusicById(musicNo);
    }

    @Override
    public Music selectMusic(int musicNo) {
        return selectMusicById(musicNo); // 내부적으로 기존 메서드 호출
    }
}