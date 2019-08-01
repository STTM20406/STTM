package kr.or.ddit.work_comment.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.work_comment.model.Work_CommentVo;
import kr.or.ddit.work_comment.service.IWork_CommentService;

@Controller
public class Work_CommentController {

	private static final Logger logger = LoggerFactory.getLogger(Work_CommentController.class);
	
	@Resource(name="work_CommentService")
	private IWork_CommentService commentService;
	
	@RequestMapping(path="/comment",method=RequestMethod.GET)
	public String workComment(Model model) {
		Work_CommentVo commentListVo = new Work_CommentVo();
		commentListVo.setWrk_id(1);
		commentListVo.setPrj_id(1);
		logger.debug("!@# commentListVo : {}",commentListVo);
		
		List<Work_CommentVo> commentList = commentService.commentList(commentListVo);
		logger.debug("!@# commentList : {}",commentList);
		
		model.addAttribute("commentList", commentList);
		return "/propertySet/setWorkComment.user.tiles";
	}
	
	@RequestMapping(path="/comment",method=RequestMethod.POST)
	public String workComment(String comm_content,HttpSession session) {

		String viewName ="";
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		
		Work_CommentVo commentVo = new Work_CommentVo();
		commentVo.setUser_email(userVo.getUser_email());
		commentVo.setComm_content(comm_content);
		commentVo.setPrj_id(1);
		commentVo.setWrk_id(1);
		
		int commCnt = commentService.commentInsert(commentVo);
		
		if(commCnt == 1) {
			viewName="redirect:/comment";
		}else {
			viewName="redirect:/comment";
		}
		
		return viewName;
	}
	
	@RequestMapping(path="/commUpdate",method=RequestMethod.POST)
	public String commentUpdate(Model model, String commContent, int commComm_id, int commPrj_id) {
		Work_CommentVo commentVo = new Work_CommentVo();
		commentVo.setComm_id(commComm_id);
		commentVo.setPrj_id(commComm_id);
		commentVo.setComm_content(commContent);
		
		int updateComm = commentService.commUpdate(commentVo);
		
		model.addAttribute("data",updateComm);
		
		return "jsonView";
	}
	
	
	@RequestMapping(path="/commDelete",method=RequestMethod.POST)
	public String commentDelete() {
		
		return "jsonView";
	}
	
}































