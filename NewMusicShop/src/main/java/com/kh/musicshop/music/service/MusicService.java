package com.kh.musicshop.music.service;

import com.kh.musicshop.music.vo.Music;

public interface MusicService {
    Music selectMusicById(int musicNo);
    Music selectMusic(int musicNo); // 선언만
}