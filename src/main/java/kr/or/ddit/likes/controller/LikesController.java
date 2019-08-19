package kr.or.ddit.likes.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.board_write.model.Board_WriteVo;
import kr.or.ddit.chat_content.service.IChat_ContentService;
import kr.or.ddit.likes.service.ILikesService;
import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.users.model.UserVo;

@Controller
public class LikesController {

	private static final Logger logger = LoggerFactory.getLogger(LikesController.class);
	
	@Resource(name="likesService")
	private ILikesService likeService;
	
	
	@RequestMapping("/addLikeAjax")
	public String addlikeAjax(String write_id, String board_id, Model model, HttpSession session) {

		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		int writeId = Integer.parseInt(write_id);
		int boardId = Integer.parseInt(board_id);
		
		Board_WriteVo bwVo = new Board_WriteVo();
		bwVo.setWrite_id(writeId);
		bwVo.setBoard_id(boardId);
		bwVo.setUser_email(userVo.getUser_email());
		
		//게시글 좋아요 증가
		int addCnt = likeService.likeAdd(bwVo);
		logger.debug("log @@@@@@@@addCnt : {}",addCnt);
		//게시글에 내가 좋아요 눌렀다는 정보 추가
		int chk = likeService.whoLikeAdd(bwVo);
		logger.debug("log @@@@@@@@chk : {}",chk);
		
		//좋아요 현재 값
		int cnt = likeService.likeCnt(writeId);
		
		logger.debug("log @@@@@@@@cnt : {}",cnt);
		
		model.addAttribute("data",cnt);
		return "jsonView";
	}
	
	@RequestMapping("/downLikeAjax")
	public String downLikeAjax(String write_id, String board_id,Model model, HttpSession session) {

		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		int writeId = Integer.parseInt(write_id);
		int boardId = Integer.parseInt(board_id);
		
		Board_WriteVo bwVo = new Board_WriteVo();
		bwVo.setWrite_id(writeId);
		bwVo.setBoard_id(boardId);
		bwVo.setUser_email(userVo.getUser_email());
		logger.debug("downlikeajax log : {}",bwVo);
		//게시글 좋아요 내가 눌렀다는 정보 삭제
		likeService.whoLikeDown(bwVo);
		
		//게시글 좋아요 감소
		likeService.likeDown(bwVo);
		
		
		
		//좋아요 현재 값
		int cnt = likeService.likeCnt(writeId);
		logger.debug("log down@@@@@@@@cnt : {}",cnt);
		
		model.addAttribute("data",cnt);
		
		return "jsonView";
	}
	
	
}
