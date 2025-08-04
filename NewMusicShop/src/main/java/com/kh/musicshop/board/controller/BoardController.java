package com.kh.musicshop.board.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.kh.musicshop.board.model.service.BoardService;
import com.kh.musicshop.board.model.vo.Board;
import com.kh.musicshop.common.model.vo.PageInfo;
import com.kh.musicshop.common.template.Pagination;

import jakarta.servlet.http.HttpSession;


import com.kh.musicshop.member.model.vo.Member;
@Controller
public class BoardController {
	
	
	//서비스 선언 
	@Autowired
	private BoardService service;
	
	
	// Controller 또는 Service 클래스 상단에 추가
	@Value("${file.dir}")
	private String filePath;
	
	
	//게시글 목록 이동 메소드
	@RequestMapping("list.bo")
	public String boardList(@RequestParam(value="currentPage",defaultValue="1")
							int currentPage
							,Model model) throws Exception {
		//자유게시판 첫 목록은 항상 1번 페이지일테니 기본값으로 1 설정해두기
		System.out.println("게시글 목록 요청");
		
		
		
		//추가적으로 필요한 값 
		int listCount = service.listCount(); //총 게시글 개수
		int boardLimit = 5; //게시글 보여줄 개수
		int pageLimit = 10; //페이징바 개수 
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
		
		//게시글 목록 조회
		ArrayList<Board> list = service.boardList(pi);
		
		
		//위임시 목록데이터 전달 
		model.addAttribute("list", list);
		model.addAttribute("pi", pi);
		
		return "board/boardListView";
	}
	
	
	
	
	//메소드명 boardDetail() - SELECT
	//조회수 증가 메소드명 increaseCount() - DML  
	//조회수 증가가 성공이라면 게시글 조회해서 상세페이지로 전달 및 이동 
	//실패시 오류발생 메시지를 담고 에러페이지로 위임처리하기
	@RequestMapping("detail.bo")
	public String boardDetail(int bno, Model model, HttpSession session) {
	    int result = service.increaseCount(bno);

	    if (result > 0) {
	        Board b = service.boardDetail(bno);
	        model.addAttribute("b", b);

	        // 🔥 세션에서 로그인 유저 꺼내서 모델에 넣기
	        Member loginUser = (Member) session.getAttribute("loginUser");
	        model.addAttribute("loginUser", loginUser);

	        return "board/boardDetailView";
	    } else {
	        model.addAttribute("errorMsg", "오류발생!");
	        return "common/errorPage";
	    }
	}
	
	
	@GetMapping("insert.bo")
	public String boardEnrollForm(@SessionAttribute(name = "loginUser", required = false) Member loginUser,
	                              HttpSession session) {
	    if (loginUser == null) {
	        session.setAttribute("alertMsg", "로그인 후 이용해주세요.");
	        return "redirect:/"; // 로그인 페이지 경로로 수정해도 OK
	    }
	    return "board/boardEnrollForm";
	}

	// 📝 게시글 등록 요청 처리
	@PostMapping("insert.bo")
	public String insertBoard(Board b,
	                          MultipartFile uploadFile,  // 첨부파일 객체
	                          HttpSession session) {     // 로그인 정보 확인을 위한 세션

	    /*
	     * ✅ 1. 로그인 사용자 확인
	     *  - 로그인한 사용자의 정보가 세션에 존재하는지 확인
	     *  - 세션에 저장된 loginUser 객체가 없으면 글 작성 불가
	     *  - 존재할 경우, 작성자의 userId를 Board 객체에 설정
	     */
	    Member loginUser = (Member) session.getAttribute("loginUser");

	    if (loginUser != null) {
	        b.setBoardWriter(loginUser.getUserId()); // 작성자 ID 설정
	    } else {
	        session.setAttribute("alertMsg", "로그인 후 이용해주세요.");
	        return "redirect:/member/loginForm"; // 로그인 페이지로 이동 (실제 경로에 맞게 수정)
	    }

	    /*
	     * ✅ 2. 첨부파일 처리
	     *  - 업로드된 파일이 있을 경우 파일명 확인
	     *  - 파일명을 중복되지 않도록 서버에 저장 (시간+랜덤 조합 등)
	     *  - Board 객체에 원본 파일명 및 서버에 저장된 파일명 설정
	     */
	    if (!uploadFile.getOriginalFilename().equals("")) {
	        // 저장 및 이름 변경
	        String changeName = saveFile(uploadFile, session);

	        // Board 객체에 파일명 저장
	        b.setOriginName(uploadFile.getOriginalFilename());               // 원본 이름
	        b.setChangeName("/uploadFiles/" + changeName);        // 변경된 저장 경로 포함
	    }

	    /*
	     * ✅ 3. 게시글 저장 서비스 호출
	     *  - service.insertBoard() 호출
	     *  - 결과값에 따라 메시지 처리
	     */
	    int result = service.insertBoard(b);

	    if (result > 0) {
	        session.setAttribute("alertMsg", "게시글 작성 성공!");
	    } else {
	        session.setAttribute("alertMsg", "게시글 작성 실패!");
	    }

	    /*
	     * ✅ 4. 등록 완료 후 목록 페이지로 리다이렉트
	     */
	    return "redirect:/list.bo";
	}
	
	
	
	
	
	
	//게시글 삭제
	@RequestMapping("delete.bo")
	public String deleteBoard(int bno,
	                          String filePath,
	                          HttpSession session) {

	    // 1. 게시글 삭제 (DB에서 delete 또는 update status='N')
	    int result = service.deleteBoard(bno);

	    if(result > 0) {
	        session.setAttribute("alertMsg", "게시글 삭제 성공");

	        // 2. 첨부파일이 있는 경우 삭제 처리
	        if(filePath != null && !filePath.trim().equals("")) {
	            // 실제 경로로 변환 후 파일 객체 생성
	            String realPath = session.getServletContext().getRealPath(filePath);
	            File file = new File(realPath);
	            
	            if(file.exists()) {
	                file.delete();
	            }
	        }

	        // 3. 성공 시 게시글 목록으로 이동
	        return "redirect:/list.bo";

	    } else {
	        session.setAttribute("alertMsg", "게시글 삭제 실패");
	        return "redirect:/detail.bo?bno=" + bno;
	    }
	}
	
	//게시글 수정페이지로 이동 메소드
	@GetMapping("update.bo")
	public String boardUpdateForm(int bno
								 ,Model model) {
		
		//글번호로 게시글 정보 조회
		Board b = service.boardDetail(bno);
		
		//위임하는 페이지에 게시글 정보 담아가기 
		model.addAttribute("b",b); 
		
		return "board/boardUpdateForm";
	}
	
	
	
	// 게시글 수정 등록작업 메소드 
	@PostMapping("update.bo")
	public String boardUpdate(Board b,
	                          MultipartFile reUploadFile,
	                          HttpSession session) {

	    // 삭제해야 할 기존 첨부파일명을 저장할 변수
	    String deleteFile = null;

	    // 새로운 첨부파일이 전달된 경우 (null 체크 + 비어있지 않음)
	    if (reUploadFile != null && !reUploadFile.isEmpty()) {

	        // 기존에 첨부파일이 있었던 경우 기존 파일명을 삭제 대상으로 저장
	        if (b.getOriginName() != null) {
	            deleteFile = b.getChangeName(); // 서버에 저장된 기존 파일명
	        }

	        // 새로운 첨부파일 업로드 수행 (서버에 저장 후 변경된 파일명 반환)
	        String changeName = saveFile(reUploadFile, session);

	        // DB에 저장될 파일 정보 설정
	        b.setOriginName(reUploadFile.getOriginalFilename()); // 원본 파일명
	        b.setChangeName(changeName); // 서버에 저장된 변경 파일명
	    }

	    // 게시글 정보 업데이트 (DB 반영)
	    int result = service.updateBoard(b);

	    if (result > 0) {
	        // 수정 성공 시 알림 메시지 저장
	        session.setAttribute("alertMsg", "게시글 수정 성공!");

	        // 기존 첨부파일이 있었으면 서버에서 삭제
	        if (deleteFile != null) {
	            // 파일 저장 경로 (예: /resources/uploadFiles/)
	            String realPath = session.getServletContext().getRealPath("/resources/uploadFiles/");
	            new File(realPath + deleteFile).delete(); // 실제 파일 삭제
	        }

	    } else {
	        // 수정 실패 시 알림 메시지 저장
	        session.setAttribute("alertMsg", "게시글 수정 실패!");
	    }

	    // 디테일 페이지로 리다이렉트
	    return "redirect:/detail.bo?bno=" + b.getBoardNo();
	}
	
	
	@RequestMapping("search.bo")
	public String searchList(@RequestParam(value="currentPage",defaultValue = "1")
							int currentPage
						   ,String condition
						   ,String keyword
						   ,Model model) {
		//검색 목록도 페이징 처리될 수 있도록 
		//페이징 처리에 필요한 데이터 준비 
		HashMap<String,String> map = new HashMap<>();
		
		map.put("condition", condition);
		map.put("keyword", keyword);
		//조건에 맞는 목록에 대한 개수를 세어와야하기 때문에 검색용 조건 데이터 담아서 전달하기 
		int searchCount = service.searchCount(map);
		int boardLimit = 5;
		int pageLimit = 10;
		
		PageInfo pi = Pagination.getPageInfo(searchCount, currentPage, pageLimit, boardLimit);
		
		ArrayList<Board> searchList = service.searchList(map,pi);
		
		model.addAttribute("map", map);
		model.addAttribute("pi", pi);
		model.addAttribute("list", searchList);
		
		return "board/boardListView";
	}
	
	
	
	
	
	
	
	
	
	// 파일 업로드 처리 메소드 (첨부파일 없을 때도 동작 가능)
	public String saveFile(MultipartFile uploadFile, HttpSession session) {
	    // 1. 비어있는 경우 방어 처리
	    if (uploadFile == null || uploadFile.isEmpty()) {
	        return null; // 또는 ""
	    }

	    // 2. 원본 파일명 추출
	    String originName = uploadFile.getOriginalFilename();

	    // 3. 시간 형식 문자열 + 랜덤값 + 확장자 조합
	    String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	    int ranNum = (int) (Math.random() * 90000 + 10000);

	    // 확장자 안전하게 추출
	    String ext = "";
	    if (originName != null && originName.contains(".")) {
	        ext = originName.substring(originName.lastIndexOf("."));
	    }

	    String changeName = currentTime + ranNum + ext;

	    // 4. 실제 서버 경로에 업로드 처리
	    try {
	        uploadFile.transferTo(new File(filePath + changeName));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    // 5. 저장된 변경파일명 리턴
	    return changeName;
	}
	
		
		
	
		
		
		
}
