package kr.or.ddit.work_comment.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.WordUtils;
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
		commentListVo.setWrk_id(110);
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
		commentVo.setWrk_id(110);
		logger.debug("!@#userId : {}",userVo.getUser_email());
		logger.debug("!@#comm_content : {}",comm_content);
		logger.debug("!@#commentVo : {}",commentVo);
		
		int commCnt = commentService.commentInsert(commentVo);
		
		if(commCnt == 1) {
			viewName="redirect:/comment";
		}else {
			viewName="redirect:/comment";
		}
		
		return viewName;
	}
	
	@RequestMapping("/commUpdate")
	public String commentUpdate(Model model, String inq_trim, String prj_id, String comm_id) {
		logger.debug("!@# inq_trim : {}",inq_trim);
		logger.debug("!@# comm_id : {}",comm_id);
		logger.debug("!@# prj_id : {}",prj_id);
		
		int prj_idStr = Integer.parseInt(prj_id);
		int comm_idStr = Integer.parseInt(comm_id);
		
		Work_CommentVo commentVo = new Work_CommentVo();
		commentVo.setComm_id(comm_idStr);
		commentVo.setPrj_id(prj_idStr);
		commentVo.setComm_content(inq_trim);
		
		int updateCommCnt = commentService.commUpdate(commentVo);
		
		model.addAttribute("data",updateCommCnt);
		
		return "jsonView";
	}
	
	
	@RequestMapping(path="/commDelete",method=RequestMethod.GET)
	public String commentDelete(String prj_id,String comm_id,Model model) {
		int prj_idStr = Integer.parseInt(prj_id);
		int comm_idStr = Integer.parseInt(comm_id);
		logger.debug("!@# prj_idStr : {}",prj_idStr);
		logger.debug("!@# comm_idStr : {}",comm_idStr);
		
		Work_CommentVo commentVo = new Work_CommentVo();
		commentVo.setPrj_id(prj_idStr);
		commentVo.setComm_id(comm_idStr);
		
		Work_CommentVo commentListVo = new Work_CommentVo();
		commentListVo.setWrk_id(110);
		commentListVo.setPrj_id(1);
		
		int deleteCnt = commentService.commDelete(commentVo);
		
		List<Work_CommentVo> commList = new ArrayList<Work_CommentVo>();
		if(deleteCnt == 1) {
			List<Work_CommentVo> commListAjax = commentService.commentList(commentListVo);
			for(int i = 0;i<commListAjax.size();i++) {
				if(commListAjax.get(i).getDel_fl().equals("N")) {
					commList.add(commListAjax.get(i));
				}
			}
			logger.debug("!@# commList : {}",commList);
			model.addAttribute("data", commList);
		}
		
		return "jsonView";
	}
	
}































