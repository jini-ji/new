package com.kh.musicshop.board.model.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.kh.musicshop.board.model.vo.Board;
import com.kh.musicshop.common.model.vo.PageInfo;

@Repository
public class BoardDao {
	
	
	//게시글 총 개수 조회 메소드
	public int listCount(SqlSessionTemplate sqlSession) {
		
		return sqlSession.selectOne("boardMapper.listCount");
	}

	//게시글 목록 조회 메소드
	public ArrayList<Board> boardList(SqlSessionTemplate sqlSession, PageInfo pi) {
		
		//페이징 처리를 위한 rowBounds 객체 준비 
		//마이바티스에서 페이징처리를 도와주는 객체 
		
		int limit = pi.getBoardLimit(); //몇개씩 보여줄건지 (조회개수)
		//몇개를 건너뛰고 조회할것인지
		//한페이지에서 5개씩 보여준다고 가정 
		//1페이지에선 1-5 보여주고 
		//2페이지에선 6-10 보여주고 
		int offset = (pi.getCurrentPage()-1)*limit;
		
		RowBounds rowBounds = new RowBounds(offset,limit);
		
		//페이징처리를 위해 rowbounds 객체를 전달할때 두번째 매개변수 위치에 전달할 파라미터 값이 없더라도 
		//형식을 유지해야되기때문에 null을 전달하고 rowbounds 객체는 3번째 매개변수로 전달해야한다.
		ArrayList<Board> list = (ArrayList)sqlSession.selectList("boardMapper.boardList",null,rowBounds);
		
		return list;
	}
	
	//조회수 증가 
	public int increaseCount(SqlSessionTemplate sqlSession, int bno) {
		
		return sqlSession.update("boardMapper.increaseCount",bno);
	}

	//게시글 상세조회
	public Board boardDetail(SqlSessionTemplate sqlSession, int bno) {
		
		return sqlSession.selectOne("boardMapper.boardDetail",bno);
	}
	
	//게시글 등록
	public int insertBoard(SqlSessionTemplate sqlSession, Board b) {
		
		return sqlSession.insert("boardMapper.insertBoard",b);
	}
	
	//게시글 삭제
	public int deleteBoard(SqlSessionTemplate sqlSession, int bno) {
		
		return sqlSession.delete("boardMapper.deleteBoard",bno);
	}
	
	//게시글 수정
	public int updateBoard(SqlSessionTemplate sqlSession, Board b) {
		
		return sqlSession.update("boardMapper.updateBoard",b);
	}
	
	//게시글 검색 개수 
	public int searchCount(SqlSessionTemplate sqlSession, HashMap<String, String> map) {
		
		return sqlSession.selectOne("boardMapper.searchCount",map);
	}
	
	//게시글 검색 목록 조회
	public ArrayList<Board> searchList(SqlSessionTemplate sqlSession, HashMap<String, String> map, PageInfo pi) {
	
		//페이징처리를 위한 RowBounds 객체 준비 
		int limit = pi.getBoardLimit(); //보여줄 개수 
		int offset = (pi.getCurrentPage()-1)*limit; //건너 뛸 개수 
		
		RowBounds rowBounds = new RowBounds(offset,limit);
		
		return (ArrayList)sqlSession.selectList("boardMapper.searchList",map,rowBounds);
	}

}
