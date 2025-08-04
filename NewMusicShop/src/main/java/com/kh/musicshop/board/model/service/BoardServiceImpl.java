package com.kh.musicshop.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.musicshop.board.model.dao.BoardDao;
import com.kh.musicshop.board.model.vo.Board;
import com.kh.musicshop.common.model.vo.PageInfo;

@Service
public class BoardServiceImpl implements BoardService{
	
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private BoardDao dao;
	
	
	@Override
	public int listCount() {
		
		return dao.listCount(sqlSession);
	}


	@Override
	public ArrayList<Board> boardList(PageInfo pi) {
		
		return dao.boardList(sqlSession,pi);
	}

	
	@Override
	public int increaseCount(int bno) {
	
		return dao.increaseCount(sqlSession,bno);
	}

	@Override
	public Board boardDetail(int bno) {

		return dao.boardDetail(sqlSession,bno);
	}
	
	
	@Override
	public int insertBoard(Board b) {
		
		return dao.insertBoard(sqlSession,b);
	}
	
	
	@Override
	public int deleteBoard(int bno) {
	
		return dao.deleteBoard(sqlSession,bno);
	}
	
	
	@Override
	public int updateBoard(Board b) {
		
		return dao.updateBoard(sqlSession,b);
		
	}
	
	
	@Override
	public int searchCount(HashMap<String, String> map) {
	
		return dao.searchCount(sqlSession,map);
	}
	
	@Override
	public ArrayList<Board> searchList(HashMap<String, String> map, PageInfo pi) {

		return dao.searchList(sqlSession,map,pi);
	}
	
}
