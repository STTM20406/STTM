package kr.or.ddit.work_comment.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.project_mem.service.IProject_MemService;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.work_comment.model.Work_CommentVo;
import kr.or.ddit.work_comment.service.IWork_CommentService;
import kr.or.ddit.work_mem_flw.model.Work_Mem_FlwVo;
import kr.or.ddit.work_mem_flw.service.IWork_Mem_FlwService;

@Controller
public class Work_CommentController {

	private static final Logger logger = LoggerFactory.getLogger(Work_CommentController.class);
	
	@Resource(name="work_CommentService")
	private IWork_CommentService commentService;
	
	@Resource(name = "project_MemService")
	private IProject_MemService projectMemService;
	
	@Resource(name="work_Mem_FlwService")
	private IWork_Mem_FlwService workMemFlwService;
	
	
	@RequestMapping("/comment")
	public @ResponseBody Map<String, Object> workComment(Model model,HttpSession session,String page,String pageSize,String wps_wrk_id) {
		logger.debug("!@# session : {}",session);
		logger.debug("!@# wps_wrk_id : {}",wps_wrk_id);
		
		
		
		int wrk_id = Integer.parseInt(wps_wrk_id);
		//배정된 업무 멤버 가져오기
		Work_Mem_FlwVo work_memVo = new Work_Mem_FlwVo(wrk_id, "M");
		List<Work_Mem_FlwVo> wrkMemList = workMemFlwService.workMemFlwList(work_memVo); 
		
		//업무 팔로워 멤버 가져오기
		Work_Mem_FlwVo work_flwVo = new Work_Mem_FlwVo(wrk_id, "F");
		List<Work_Mem_FlwVo> wrkFlwList = workMemFlwService.workMemFlwList(work_flwVo); 
		
		
		
		int pageStr = page == null ? 1 : Integer.parseInt(page);
		int pageSizeStr =  pageSize == null ? 10 : Integer.parseInt(pageSize);
		ProjectVo projectVo = (ProjectVo) session.getAttribute("PROJECT_INFO");
		
		PageVo pageVo = new PageVo(pageStr,pageSizeStr);
		pageVo.setPrj_id(projectVo.getPrj_id());
		pageVo.setWrk_id(wrk_id);
		
		logger.debug("!@# projectVo : {}",projectVo);
		logger.debug("!@# projectVo.getPrj_id() : {}",projectVo.getPrj_id());
		logger.debug("!@# pageVo : {}",pageVo);
		
		Map<String, Object> resultMap = commentService.commentList(pageVo);
		List<Work_CommentVo> commentList = (List<Work_CommentVo>) resultMap.get("commentList");
		int commPageSize = (int) resultMap.get("commPagenationSize");
		logger.debug("!@# commentList : {}",commentList);
		
		model.addAttribute("commentList", commentList);
		
		resultMap.put("wrkMemList",wrkMemList);
		resultMap.put("wrkFlwList",wrkFlwList);
		resultMap.put("commentList",commentList);
		resultMap.put("pageVo",pageVo);
		resultMap.put("commPageSize",commPageSize);
		
		return resultMap;
	}
	
	@RequestMapping("/commentInsert")
	public String workComment(String comm_content,HttpSession session,String wps_wrk_id) {

		String viewName ="";
		int wrk_id = Integer.parseInt(wps_wrk_id);
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		ProjectVo projectVo = (ProjectVo) session.getAttribute("PROJECT_INFO");
		
		Work_CommentVo commentVo = new Work_CommentVo();
		commentVo.setUser_email(userVo.getUser_email());
		commentVo.setComm_content(comm_content);
		commentVo.setPrj_id(projectVo.getPrj_id());
		commentVo.setWrk_id(wrk_id);
		logger.debug("!@#userId : {}",userVo.getUser_email());
		logger.debug("!@#comm_content : {}",comm_content);
		logger.debug("!@#commentVo : {}",commentVo);
		
		int commCnt = commentService.commentInsert(commentVo);
		
		return "jsonView";
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
	
	
	@RequestMapping("/commDelete")
	public String commentDelete(String comm_id,Model model,String page,String pageSize,String wps_wrk_id,HttpSession session) {
		int pageStr = page == null ? 1 : Integer.parseInt(page);
		int pageSizeStr =  pageSize == null ? 10 : Integer.parseInt(pageSize);
		ProjectVo projectVo = (ProjectVo) session.getAttribute("PROJECT_INFO");
		
		logger.debug("!@# wps_wrk_id : {}",wps_wrk_id);
		PageVo pageVo = new PageVo(pageStr,pageSizeStr);
		
		int comm_idStr = Integer.parseInt(comm_id);
		int wrk_id = Integer.parseInt(wps_wrk_id);
		logger.debug("!@# comm_idStr : {}",comm_idStr);
		
		Work_CommentVo commentVo = new Work_CommentVo();
		commentVo.setComm_id(comm_idStr);
		
		commentVo.setWrk_id(wrk_id);
		commentVo.setPrj_id(projectVo.getPrj_id());
		
		int deleteCnt = commentService.commDelete(commentVo);
		
		logger.debug("!@# deleteCnt : {}",deleteCnt);
//		List<Work_CommentVo> commList = new ArrayList<Work_CommentVo>();
//		if(deleteCnt == 1) {
//			List<Work_CommentVo> commListAjax = (List<Work_CommentVo>) commentService.commentList(pageVo);
//			for(int i = 0;i<commListAjax.size();i++) {
//				if(commListAjax.get(i).getDel_fl().equals("N")) {
//					commList.add(commListAjax.get(i));
//				}
//			}
//			logger.debug("!@# commList : {}",commList);
//			model.addAttribute("data", commList);
//		}
		
		return "jsonView";
	}
	
}































