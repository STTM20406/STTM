package kr.or.ddit.project.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ResponseBody;

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
		
		projectService.updateProject(projectVo);
		
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
	public @ResponseBody HashMap<String, Object> propertySetAjax(String prj_id, Model model, HttpSession session) {
		
		int prjId = Integer.parseInt(prj_id);
		
		//세션에 저장된 user 정보를 가져옴
		UserVo user = (UserVo) session.getAttribute("USER_INFO");    
		String user_email = user.getUser_email();
		
		Project_MemVo projectMemVo = new Project_MemVo();
		projectMemVo.setPrj_id(prjId);
		projectMemVo.setUser_email(user_email);
		
		//프로젝트 멤버의 레벨이 0인 멤버만 리스트에 담기
		List<Project_MemVo> admList = new ArrayList<Project_MemVo>();
		List<Project_MemVo> project_adm_list = projectMemService.projectMemList(projectMemVo);
		
		for(int i=0; i<project_adm_list.size(); i++) {
			if(project_adm_list.get(i).getPrj_mem_lv().equals("LV0")) {
				admList.add(project_adm_list.get(i));
			}
		}
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("projectInfo", projectService.getProject(prjId));
		hashmap.put("projectAdmList", admList);
		hashmap.put("projectMemList", project_adm_list);

		return hashmap;
	}
	
	//프로젝트 설정 업데이트
	@RequestMapping("/propertySetItemAjax")
	public String propertySetItemAjax(String prj_id, String prj_nm, String prj_exp, String prj_auth, String prj_st, 
			String prj_start_dt, String prj_end_dt, String prj_cmp_dt, Model model, HttpSession session) throws ParseException {

		int prjId = Integer.parseInt(prj_id);
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		ProjectVo projectVo = new ProjectVo(prjId, prj_nm, prj_exp, prj_auth, prj_st);
		
		if(prj_nm.contentEquals("")) {
			projectVo.setPrj_nm(projectVo.getPrj_nm());
		}
		
		if(!prj_start_dt.contentEquals("") )
			projectVo.setPrj_start_dt(sdf.parse(prj_start_dt));
		
		if(!prj_end_dt.contentEquals("") )
			projectVo.setPrj_end_dt(sdf.parse(prj_end_dt));
		
		if(!prj_cmp_dt.contentEquals("") )
			projectVo.setPrj_cmp_dt(sdf.parse(prj_cmp_dt));

		int updateCnt = projectService.updateAllProject(projectVo);
		
		if(updateCnt != 0) {
			model.addAttribute("data", projectService.getProject(projectVo.getPrj_id()));
		}
		
		return "jsonView";
	}
	
	//프로젝트 멤버 리스트 불러오기(관리자 추가)
	@RequestMapping("/projectAdmListAjax")
	public String projectAdmListAjax(String prj_id, Model model, HttpSession session) {
		
		int prjId = Integer.parseInt(prj_id);
		
		//세션에 저장된 user 정보를 가져옴
		UserVo user = (UserVo) session.getAttribute("USER_INFO");    
		String user_email = user.getUser_email();
		
		Project_MemVo projectMemVo = new Project_MemVo();
		projectMemVo.setPrj_id(prjId);
		projectMemVo.setUser_email(user_email);
		
		model.addAttribute("data", projectMemService.projectMemList(projectMemVo));
		
		return "jsonView";
	}
	
	//프로젝트 멤버 관리자로 update
	@RequestMapping("/projectAdmAddAjax")
	public String projectAdmAddAjax(String user_email, String prj_id, Model model) {
		
		int prjId = Integer.parseInt(prj_id);
		
		Project_MemVo projectMemVo = new Project_MemVo();
		projectMemVo.setUser_email(user_email);
		projectMemVo.setPrj_id(prjId);
		projectMemVo.setPrj_mem_lv("LV0");
		
		int updateCnt = projectMemService.updateProjectMem(projectMemVo);
		
		Project_MemVo projectMemListVo = new Project_MemVo();
		projectMemListVo.setPrj_id(prjId);
		projectMemListVo.setUser_email(user_email);
		
		List<Project_MemVo> admList = new ArrayList<Project_MemVo>();
		
		if(updateCnt != 0) {
			List<Project_MemVo> project_adm_list = projectMemService.projectMemList(projectMemVo);
			for(int i=0; i<project_adm_list.size(); i++) {
				if(project_adm_list.get(i).getPrj_mem_lv().equals("LV0")) {
					admList.add(project_adm_list.get(i));
				}
			}
			model.addAttribute("data", admList);
		}
		return "jsonView";
	}
	
	//프로젝트 관리자 삭제
	@RequestMapping("/projectAdmDelAjax")
	public String projectAdmDelAjax(String user_email, String prj_id, Model model) {
		
		int prjId = Integer.parseInt(prj_id);
		
		Project_MemVo projectMemVo = new Project_MemVo();
		projectMemVo.setUser_email(user_email);
		projectMemVo.setPrj_id(prjId);
		projectMemVo.setPrj_mem_lv("LV1");
		
		int updateCnt = projectMemService.updateProjectMem(projectMemVo);
		List<Project_MemVo> admList = new ArrayList<Project_MemVo>();
		
		if(updateCnt != 0) {
			List<Project_MemVo> project_adm_list = projectMemService.projectMemList(projectMemVo);
			for(int i=0; i<project_adm_list.size(); i++) {
				if(project_adm_list.get(i).getPrj_mem_lv().equals("LV0")) {
					admList.add(project_adm_list.get(i));
				}
			}
			model.addAttribute("data", admList);
		}
		return "jsonView";
	}
	
	//프로젝트 멤버 리스트 불러오기
	@RequestMapping("/projectMemListAjax")
	public String projectMemListAjax(String prj_id, Model model, HttpSession session) {
		
		int prjId = Integer.parseInt(prj_id);
		
		//세션에 저장된 user 정보를 가져옴
		UserVo user = (UserVo) session.getAttribute("USER_INFO");    
		String user_email = user.getUser_email();
		
		Project_MemVo projectMemVo = new Project_MemVo();
		projectMemVo.setPrj_id(prjId);
		projectMemVo.setUser_email(user_email);
		
		//내가 속한 프로젝트의 멤버들을 중복 없이 가져와 리스트에 담기
		List<Project_MemVo> project_mem_list = projectMemService.projectAllMemList(user_email);
		List<Project_MemVo> project_adm_list = projectMemService.projectMemList(projectMemVo);
		
		
		for(int i=0; i<project_adm_list.size(); i++) {
			for(int j=0; j<project_mem_list.size(); j++) {
				if(project_mem_list.get(j).getUser_email().equals(project_adm_list.get(i).getUser_email())) {
					project_mem_list.remove(project_mem_list.get(j));
				}
			}
		}
		
		model.addAttribute("data", project_mem_list);
		
		return "jsonView";
	}
	
	
	@RequestMapping("/projectMemAddAjax")
	public @ResponseBody HashMap<String, Object> projectMemAddAjax(String user_email, String prj_id, Model model) {
		
		int prjId = Integer.parseInt(prj_id);
		
		Project_MemVo projectMemVo = new Project_MemVo();
		projectMemVo.setUser_email(user_email);
		projectMemVo.setPrj_id(prjId);
		projectMemVo.setPrj_mem_lv("LV1");
		projectMemVo.setPrj_own_fl("N");
		
		int insertCnt = projectMemService.insertProjectMem(projectMemVo);
		
		Project_MemVo projectMemListVo = new Project_MemVo();
		projectMemListVo.setUser_email(user_email);
		projectMemListVo.setPrj_id(prjId);
		
		
		//내가 속한 프로젝트의 멤버들을 중복 없이 가져와 리스트에 담기
		List<Project_MemVo> project_mem_list = projectMemService.projectAllMemList(user_email);
		List<Project_MemVo> project_adm_list = projectMemService.projectMemList(projectMemListVo);
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		
		if(insertCnt != 0) {
			for(int i=0; i<project_adm_list.size(); i++) {
				for(int j=0; j<project_mem_list.size(); j++) {
					if(project_mem_list.get(j).getUser_email().equals(project_adm_list.get(i).getUser_email())) {
						project_mem_list.remove(project_mem_list.get(j));
					}
				}
			}
		}
		
		hashmap.put("projectAdmList", project_adm_list);
		hashmap.put("projectMemList", project_mem_list);
		
		return hashmap;
	}
	
	//프로젝트 관리자 삭제
	@RequestMapping("/projectMemDelAjax")
	public @ResponseBody HashMap<String, Object> projectMemDelAjax(String user_email, String prj_id, Model model) {
		
		int prjId = Integer.parseInt(prj_id);
		
		Project_MemVo projectMemVo = new Project_MemVo();
		projectMemVo.setUser_email(user_email);
		projectMemVo.setPrj_id(prjId);
		
		int deleteCnt = projectMemService.deleteProjectMem(projectMemVo);
		
		Project_MemVo projectMemListVo = new Project_MemVo();
		projectMemListVo.setUser_email(user_email);
		projectMemListVo.setPrj_id(prjId);
		
		
		//내가 속한 프로젝트의 멤버들을 중복 없이 가져와 리스트에 담기
		List<Project_MemVo> project_mem_list = projectMemService.projectAllMemList(user_email);
		List<Project_MemVo> project_adm_list = projectMemService.projectMemList(projectMemListVo);
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		
		if(deleteCnt != 0) {
			for(int i=0; i<project_adm_list.size(); i++) {
				for(int j=0; j<project_mem_list.size(); j++) {
					if(project_mem_list.get(j).getUser_email().equals(project_adm_list.get(i).getUser_email())) {
						project_mem_list.remove(project_mem_list.get(j));
					}
				}
			}
		}
		
		hashmap.put("projectAdmList", project_adm_list);
		hashmap.put("projectMemList", project_mem_list);
		
		return hashmap;
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
