package kr.or.ddit.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import kr.or.ddit.project_mem.model.Project_MemVo;
import kr.or.ddit.project_mem.service.IProject_MemService;
import kr.or.ddit.users.model.UserVo;



@Controller
@RequestMapping("/project")
public class ProjectController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);
	
	@Resource(name = "projectService")
	private IProjectService projectService;
	
	@Resource(name = "project_MemService")
	private IProject_MemService projectMemService;
	
	
	//프로젝트 리스트 조회
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public String projectView(Model model, HttpSession session){
		
		//세션에 저장된 user 정보를 가져옴
		UserVo user = (UserVo) session.getAttribute("USER_INFO");    
		String user_email = user.getUser_email();

		model.addAttribute("projectList", projectService.projectList(user_email));
		
		return "/projectList/projectList.user.tiles";
	}
	
	//프로젝트 리스트 상태 변경
	@RequestMapping("/prjStAjax")
	public String prjStAjax(String prj_st, String prj_id, Model model) {
		
		int prjId = Integer.parseInt(prj_id);
		//프로젝트 업데이트 할 때 쿼리가 전체 항목 업데이트로 되어있길래 현재 프로젝트의 아이디로 프로젝트 정보를 가져오는 쿼리 만들었어 //오옹 고마옹
		ProjectVo projectVo = projectService.getProject(prjId);
		projectVo.setPrj_st(prj_st);
		
		projectService.updqteProject(projectVo);
		
		model.addAttribute("data", projectService.getProject(prjId));
		
		return "jsonView";
	}
	
	
	//프로젝트 상태에 따른 리스트 조회
	@RequestMapping("/prjStListAjax")
	public String prjStListAjax(String prj_st, Model model, HttpSession session) {
		
		//세션에 저장된 user 정보를 가져옴
		UserVo user = (UserVo) session.getAttribute("USER_INFO");    
		String user_email = user.getUser_email();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_email", user_email);
		map.put("prj_st", prj_st);
		
		if(!prj_st.equals("전체프로젝트")) {
			Map<String, Object> projectList = projectService.projectStatusList(map);
			model.addAttribute("data", projectList);
		}else {
			List<ProjectVo> projectList = projectService.projectList(user_email);
			model.addAttribute("data", projectList);
		}
		
		return "jsonView";
	}
	

	//프로젝트명으로 프로젝트 검색
	@RequestMapping("/prjSearchAjax")
	public String prjSearchAjax(String prj_nm, Model model, HttpSession session) {
		//세션에 저장된 user 정보를 가져옴
		UserVo user = (UserVo) session.getAttribute("USER_INFO");    
		String user_email = user.getUser_email();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_email", user_email);
		map.put("prj_nm", prj_nm);
		
		Map<String, Object> projectList = projectService.projectSearch(map);
		model.addAttribute("data", projectList);
		
		return "jsonView";
	}
	
	
	//프로젝트 설정
	@RequestMapping("/propertySetAjax")
	public String propertySetAjax(String prj_id, Model model, HttpSession session) {
		
		int prjId = Integer.parseInt(prj_id);
		model.addAttribute("data", projectService.getProject(prjId));
		
		return "jsonView";
	}
	
	
	//프로젝트를 생성하는 동시에 프로젝트 멤버에 생성자 insert
	@RequestMapping(path = "/form", method = RequestMethod.POST)
	public String porjectForm(Model model, ProjectVo projectVo, HttpSession session) {
		
		String viewName = "";
		
		//세션에 저장된 user 정보를 가져옴
		UserVo user = (UserVo) session.getAttribute("USER_INFO");    
		String user_email = user.getUser_email();
		
		projectVo.setPrj_st("상태없음");
		
		int insertProjectCnt = projectService.insertProject(projectVo);
		
		Project_MemVo projectMemVo = new Project_MemVo();
		projectMemVo.setPrj_id(projectVo.getPrj_id()); 		//프로젝트멤버 - 프로젝트 ID 셋팅
		projectMemVo.setUser_email(user_email); 			//프로젝트멤버 - 멤버 이메일 셋팅
		projectMemVo.setPrj_mem_lv("LV0");					//프로젝트멤버 - 멤버 레벨 셋팅
		projectMemVo.setPrj_own_fl("Y");					//프로젝트멤버 - 멤버 소유 유무 셋팅
		
		int insertProjectMemCnt = projectMemService.insertProjectMem(projectMemVo);
		
		//값이 0이 아닐때
		if(insertProjectCnt != 0 && insertProjectMemCnt != 0) {
			viewName = "/projectList/projectList.user.tiles";
			model.addAttribute("projectList", projectService.projectList(user_email));
		}
		
		return viewName;
	}
}
