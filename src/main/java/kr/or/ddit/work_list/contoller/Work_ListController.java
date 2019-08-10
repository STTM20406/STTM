package kr.or.ddit.work_list.contoller;

import java.util.List;

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
import kr.or.ddit.work.model.WorkVo;
import kr.or.ddit.work.service.IWorkService;
import kr.or.ddit.work_list.model.Work_ListVo;
import kr.or.ddit.work_list.service.IWork_ListService;

@Controller
@RequestMapping("/work")
public class Work_ListController {
	
	private static final Logger logger = LoggerFactory.getLogger(Work_ListController.class);
	
	@Resource(name = "projectService")
	private IProjectService projectService;
	
	@Resource(name = "work_ListService")
	private IWork_ListService workListService;
	
	@Resource(name = "workService")
	private IWorkService workService;
	
	// 프로젝트 리스트 조회
	@RequestMapping(path = "/list", method = RequestMethod.POST)
	public String workView(String prj_id, Model model, HttpSession session) {
		
		int prjId = Integer.parseInt(prj_id);
		ProjectVo projectVo = projectService.getProject(prjId);
		List<Work_ListVo> workList = workListService.workList(prjId);

//		Work_ListVo workListVo = new Work_ListVo();
//		List<WorkVo> work = null;
		
//		for(int i=0; i<workList.size(); i++) {
//			
//			int wrkListId = workList.get(i).getWrk_lst_id();
//			int workPrjId = workList.get(i).getPrj_id();
//			
//			
//			logger.debug("wrkListId :::::::::::: log {}", wrkListId);
//			logger.debug("workPrjId :::::::::::: log {}", workPrjId);
//			
//			workListVo.setPrj_id(workPrjId);
//			workListVo.setWrk_lst_id(wrkListId);
//			
//			work = workService.getWork(workListVo);
//		}
		
		List<WorkVo> wrk = workService.getWork(8);
		
		logger.debug("workList:::::::::::log {}", workList);
		logger.debug("work:::::::::::log {}", wrk);
		
		//선택한 프로젝트의 정보를 세션에 담음 
		session.setAttribute("PROJECT_INFO", projectVo);
		model.addAttribute("workList", workList);
//		model.addAttribute("work", work);
		model.addAttribute("PROJECT_INFO", projectVo);
		
		return "/main/work/work.user.tiles";
	}

}
