package com.kh.musicshop.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.kh.musicshop.board.model.vo.Board;
import com.kh.musicshop.common.model.vo.PageInfo;

public interface BoardService {

	//게시글 개수 조회 메소드
	int listCount();

	//게시글 목록 조회
	ArrayList<Board> boardList(PageInfo pi);

	//게시글 조회수 증가
	int increaseCount(int bno);

	//게시글 상세조회
	Board boardDetail(int bno);

	//게시글 작성
	int insertBoard(Board b);

	//게시글 수정
	int updateBoard(Board b);
	
	//게시글 삭제
	int deleteBoard(int bno);

	//게시글 검색 개수 
	int searchCount(HashMap<String, String> map);

	//게시글 검색 목록조회
	ArrayList<Board> searchList(HashMap<String, String> map, PageInfo pi);
	
	
	//댓글 작성
	
	//댓글 목록
	
	
	
	
	
}
