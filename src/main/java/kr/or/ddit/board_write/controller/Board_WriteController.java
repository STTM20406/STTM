package kr.or.ddit.board_write.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.bd_inquiry.model.Bd_InquiryVo;
import kr.or.ddit.board_answer.model.Board_AnswerVo;
import kr.or.ddit.board_answer.service.IBoard_AnswerService;
import kr.or.ddit.board_write.model.Board_WriteVo;
import kr.or.ddit.board_write.model.PostReplyVo;
import kr.or.ddit.board_write.service.IBoard_WriteService;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.users.model.UserVo;

@Controller
public class Board_WriteController {
	
	private static final Logger logger = LoggerFactory.getLogger(Board_WriteController.class);
	
	@Resource(name="board_WriteService")
	private IBoard_WriteService writeService;
	
	@Resource(name="board_AnswerService")
	private IBoard_AnswerService answerService;
	
	/**
	 * Method 		: boardPostList
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-30 최초 생성
	 * @param model
	 * @param page
	 * @param pageSize
	 * @param board_id
	 * @return
	 * Method 설명 	: 게시판 게시글 페이징리스트
	 */
	@RequestMapping(path = "/community",method=RequestMethod.GET)
	public String boardPostList(Model model,String page, String pageSize,int board_id,HttpSession session) {
		
		int pageStr = page == null ? 1 : Integer.parseInt(page);
		int pageSizeStr =  pageSize == null ? 10 : Integer.parseInt(pageSize);
		
		
		PageVo pageVo = new PageVo(pageStr,pageSizeStr);
		pageVo.setBoard_id(board_id);
		
		Map<String, Object> resultMap =  writeService.boardPostList(pageVo);
		
		List<Board_WriteVo> boardList = (List<Board_WriteVo>) resultMap.get("boardPostList");
		logger.debug("!@# boardList : {}",boardList);
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		List<Board_AnswerVo> myReplyList = answerService.myReplyList(userVo.getUser_email());
		
		int paginationSize = (Integer) resultMap.get("paginationSize");
		
		model.addAttribute("myReplyList",myReplyList);
		model.addAttribute("board_id",board_id);
		model.addAttribute("boardList", boardList);
		
		return "/board/community/communityList.user.tiles";
	}
	
	/**
	 * Method 		: boardWrite
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-30 최초 생성
	 * @param model
	 * @param boardnum
	 * @return
	 * Method 설명 	: 게시글 작성하기위한 게시판 고유번호 값 가져오기.
	 */
	@RequestMapping(path = "/postAdd",method=RequestMethod.GET)
	public String boardWrite(Model model,int boardnum) {
		logger.debug("!@# boardnum : {}",boardnum);
		
		model.addAttribute("boardnum", boardnum);
		return "/board/community/communityWrite.user.tiles";
	}
	
	/**
	 * Method 		: boardWrite
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-30 최초 생성
	 * @param model
	 * @param subject
	 * @param smarteditor
	 * @param user_email
	 * @param boardnum
	 * @return
	 * Method 설명 	: 게시글 작성
	 */
	@RequestMapping(path = "/postAdd",method=RequestMethod.POST)
	public String boardWrite(Model model,String subject,String smarteditor,String user_email,int boardnum) {
		
		String viewName ="";
		
		Board_WriteVo writeVo = new Board_WriteVo(boardnum, user_email, subject, smarteditor);
		logger.debug("!@# writeVo : {}",writeVo);
		int writeCnt = writeService.insertPost(writeVo);
		
		if(writeCnt == 1) {
			viewName="redirect:/community?board_id="+boardnum;
		}else {
			viewName="redirect:/community?board_id="+boardnum;
		}
		
		return viewName;
	}
	
	/**
	 * Method 		: boardPost
	 * 작성자 			: 양한솔 
	 * 변경이력 		: 2019-07-30 최초 생성
	 * @param model
	 * @param write_id
	 * @param session
	 * @return
	 * Method 설명 	: 게시글 상세 정보
	 */
	@RequestMapping(path = "/postView", method=RequestMethod.GET)
	public String boardPost(Model model,int write_id,HttpSession session) {
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		
		// 게시글 정보
		Board_WriteVo writeVo = writeService.postInfo(write_id);
		
		// 게시글 조회수증가
		if(!writeVo.getUser_email().equals(userVo.getUser_email())) {
			int postViewCnt = writeService.postViewCnt(write_id);
		}

		// 게시글 댓글 리스트
		List<Board_AnswerVo> replyList = answerService.replyList(write_id);
		logger.debug("!@# replyList : {}",replyList);
		
		// 게시글 댓글 개수
		int replyCnt = answerService.replyCnt(write_id);
		
		
		model.addAttribute("replyCnt",replyCnt);
		model.addAttribute("replyList", replyList);
		model.addAttribute("writeInfo", writeVo);
		
		return "/board/community/communityView.user.tiles";
	}
	
	@RequestMapping(path="/postView",method=RequestMethod.POST)
	public String boardPost(String r_content,int write_id, int board_id, String user_email) {
		
		String viewName ="";
		
		Board_AnswerVo replyVo = new Board_AnswerVo(write_id, user_email, r_content);
		
		int replyCnt = answerService.insertReply(replyVo);
		logger.debug("!@# replyVo : {}",replyVo);
		
		if(replyCnt == 1) {
			viewName ="redirect:/postView?write_id="+ write_id;
		}else {
			viewName ="redirect:/postView?write_id="+ write_id;
		}
		 
		return viewName;
	}
	
	
	@RequestMapping(path="/postModify",method=RequestMethod.GET)
	public String postModify(Model model, int board_id, int write_id) {
		
		// 게시글 정보
		Board_WriteVo writeVo = writeService.postInfo(write_id);
		
		model.addAttribute("writeInfo", writeVo);
		model.addAttribute("board_id", board_id);
		 
		return "/board/community/communityUpdate.user.tiles";
	}
	
	@RequestMapping(path="/postModify",method=RequestMethod.POST)
	public String postModify(Model model, int board_id, int write_id, String smarteditor, String subject) {
	
		String viewName="";
		Board_WriteVo writeVo = new Board_WriteVo(board_id, write_id, subject, smarteditor);
		
		int updateCnt = writeService.updatePost(writeVo);
		
		if(updateCnt == 1 ) {
			viewName = "redirect:/postView?write_id="+write_id;
		}else {
			viewName="redirect:/community";
		}
		
		
		return viewName;
	}
	
	@RequestMapping("/postDelete")
	public String postDelete(Model model,int board_id, int write_id) {
		
		String viewName = "";
		
		int deleteCnt = writeService.deletePost(write_id);
		
		if(deleteCnt == 1) {
			viewName = "redirect:/community?board_id="+board_id;
		}
		
		return viewName;
	}
	
	
	@RequestMapping(path="/replyDelete",method=RequestMethod.POST)
	public String replyDelete(int board_id, int write_id, int replynum1) {
		
		String viewName="";
		logger.debug("!@# replynum1 : {}",replynum1);
		int replyCnt = answerService.deleteReply(replynum1);
		
		if(replyCnt == 1) {
			viewName ="redirect:/postView?board_id="+board_id+"&write_id="+write_id;
		}else{
			viewName ="redirect:/postView?board_id="+board_id+"&write_id="+write_id;
		}
		
		
		return viewName;
	}
	
}
