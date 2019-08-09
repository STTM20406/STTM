package kr.or.ddit.work_list.contoller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.project.service.IProjectService;

@Controller
@RequestMapping("/work")
public class Work_ListController {
	
	private static final Logger logger = LoggerFactory.getLogger(Work_ListController.class);
	
	@Resource(name = "projectService")
	private IProjectService projectService;
	
	// 프로젝트 리스트 조회
	@RequestMapping(path = "/list", method = RequestMethod.POST)
	public String projectView(String prj_id, Model model, HttpSession session) {
		
		logger.debug("prj_id ::::::::::: log {}", prj_id);
		int prjId = Integer.parseInt(prj_id);
		ProjectVo projectVo = projectService.getProject(prjId);
		logger.debug("projectVo ::::::::::: log {}", projectVo);
		
		session.setAttribute("PROJECT_INFO", projectVo);
		return "/main/work/work.user.tiles";
	}

}
