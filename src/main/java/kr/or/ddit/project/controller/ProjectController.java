package kr.or.ddit.project.controller;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.poi.hpsf.Array;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.chat_content.service.IChat_ContentService;
import kr.or.ddit.chat_mem.model.Chat_MemVo;
import kr.or.ddit.chat_mem.service.IChat_MemService;
import kr.or.ddit.chat_room.model.Chat_RoomVo;
import kr.or.ddit.chat_room.service.IChat_RoomService;
import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.project.service.IProjectService;
import kr.or.ddit.project_mem.model.Project_MemVo;
import kr.or.ddit.project_mem.service.IProject_MemService;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.work.model.WorkVo;
import kr.or.ddit.work_list.model.Work_ListVo;
import kr.or.ddit.work_list.service.IWork_ListService;

@Controller
@RequestMapping("/project")
public class ProjectController {

	private static final Logger logger = LoggerFactory.getLogger(ProjectController.class);

	@Resource(name = "projectService")
	private IProjectService projectService;

	@Resource(name = "project_MemService")
	private IProject_MemService projectMemService;
	
	@Resource(name="chat_RoomService")
	private IChat_RoomService roomService;
	
	@Resource(name="chat_MemService")
	private IChat_MemService memService;
	
	@Resource(name="chat_ContentService")
	private IChat_ContentService contentService;
	
	@Resource(name = "work_ListService")
	private IWork_ListService workListService;

	// 프로젝트 리스트 조회
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public String projectView(Model model, HttpSession session) {

		// 세션에 저장된 user 정보를 가져옴
		UserVo user = (UserVo) session.getAttribute("USER_INFO");
		String user_email = user.getUser_email();

		model.addAttribute("projectList", projectService.projectList(user_email));

		return "/projectList/projectList.user.tiles";
	}

	// 프로젝트 리스트 상태 변경
	@RequestMapping("/prjStAjax")
	public String prjStAjax(String prj_st, String prj_id, Model model) {

		int prjId = Integer.parseInt(prj_id);
		// 프로젝트 업데이트 할 때 쿼리가 전체 항목 업데이트로 되어있길래 현재 프로젝트의 아이디로 프로젝트 정보를 가져오는 쿼리 만들었어 //오옹
		// 고마옹
		ProjectVo projectVo = projectService.getProject(prjId);
		projectVo.setPrj_st(prj_st);

		projectService.updateProject(projectVo);

		model.addAttribute("data", projectService.getProject(prjId));

		return "jsonView";
	}

	// 프로젝트 상태에 따른 리스트 조회
	@RequestMapping("/prjStListAjax")
	public String prjStListAjax(String prj_st, Model model, HttpSession session) {

		// 세션에 저장된 user 정보를 가져옴
		UserVo user = (UserVo) session.getAttribute("USER_INFO");
		String user_email = user.getUser_email();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_email", user_email);
		map.put("prj_st", prj_st);

		if (!prj_st.equals("전체프로젝트")) {
			Map<String, Object> projectList = projectService.projectStatusList(map);
			model.addAttribute("data", projectList);
		} else {
			List<ProjectVo> projectList = projectService.projectList(user_email);
			model.addAttribute("data", projectList);
		}

		return "jsonView";
	}

	// 프로젝트명으로 프로젝트 검색
	@RequestMapping("/prjSearchAjax")
	public String prjSearchAjax(String prj_nm, Model model, HttpSession session) {
		// 세션에 저장된 user 정보를 가져옴
		UserVo user = (UserVo) session.getAttribute("USER_INFO");
		String user_email = user.getUser_email();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user_email", user_email);
		map.put("prj_nm", prj_nm);

		Map<String, Object> projectList = projectService.projectSearch(map);
		model.addAttribute("data", projectList);

		return "jsonView";
	}

	// 프로젝트 설정
	@RequestMapping("/propertySetAjax")
	public @ResponseBody HashMap<String, Object> propertySetAjax(String prj_id, Model model, HttpSession session) {

		int prjId = Integer.parseInt(prj_id);

		// 세션에 저장된 user 정보를 가져옴
		UserVo user = (UserVo) session.getAttribute("USER_INFO");
		String user_email = user.getUser_email();

		Project_MemVo projectMemVo = new Project_MemVo();
		projectMemVo.setPrj_id(prjId);
		projectMemVo.setUser_email(user_email);

		// 프로젝트 멤버의 레벨이 0인 멤버만 리스트에 담기
		List<Project_MemVo> admList = new ArrayList<Project_MemVo>();
		List<Project_MemVo> project_adm_list = projectMemService.projectMemList(projectMemVo);

		// 현재 접속한 사용자의 프로젝트 멤버 정보 매칭해서 가져오기
		Project_MemVo projectMemInfo = new Project_MemVo(prjId, user_email);
		Project_MemVo userInfo = projectMemService.getProjectMemInfo(projectMemInfo);

		for (int i = 0; i < project_adm_list.size(); i++) {
			if (project_adm_list.get(i).getPrj_mem_lv().equals("LV0")) {
				admList.add(project_adm_list.get(i));
			}
		}

		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("projectInfo", projectService.getProject(prjId));
		hashmap.put("projectAdmList", admList);
		hashmap.put("projectMemList", project_adm_list);
		hashmap.put("userInfo", userInfo);

		return hashmap;
	}

	// 프로젝트 설정 업데이트
	@RequestMapping("/propertySetItemAjax")
	public @ResponseBody HashMap<String, Object> propertySetItemAjax(String prj_id, String prj_nm, String prj_exp, String prj_auth, String prj_st,
			String prj_start_dt, String prj_end_dt, String prj_cmp_dt, Model model, HttpSession session) throws ParseException {

		// 세션에 저장된 user 정보를 가져옴
		UserVo user = (UserVo) session.getAttribute("USER_INFO");
		int prjId = Integer.parseInt(prj_id);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		ProjectVo projectVo = new ProjectVo(prjId, prj_nm, prj_exp, prj_auth, prj_st);

		if (prj_nm.contentEquals("")) {
			projectVo.setPrj_nm(projectVo.getPrj_nm());
		}

		if (!prj_start_dt.contentEquals(""))
			projectVo.setPrj_start_dt(sdf.parse(prj_start_dt));

		if (!prj_end_dt.contentEquals(""))
			projectVo.setPrj_end_dt(sdf.parse(prj_end_dt));

		if (!prj_cmp_dt.contentEquals(""))
			projectVo.setPrj_cmp_dt(sdf.parse(prj_cmp_dt));

		int updateCnt = projectService.updateAllProject(projectVo);

		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		
		Project_MemVo projectMemVo = new Project_MemVo();
		projectMemVo.setPrj_id(prjId);
		projectMemVo.setUser_email(user.getUser_email());

		// 프로젝트 멤버의 레벨이 0인 멤버만 리스트에 담기
		List<Project_MemVo> project_mem_list = projectMemService.projectMemList(projectMemVo);
		
		if (updateCnt != 0) {
			hashmap.put("data", projectService.getProject(projectVo.getPrj_id()));
			hashmap.put("project_mem_list", project_mem_list);
		}

		return hashmap;
	}

	// 프로젝트 멤버 리스트 불러오기(관리자 추가)
	@RequestMapping("/projectAdmListAjax")
	public String projectAdmListAjax(String prj_id, Model model, HttpSession session) {

		int prjId = Integer.parseInt(prj_id);

		// 세션에 저장된 user 정보를 가져옴
		UserVo user = (UserVo) session.getAttribute("USER_INFO");
		String user_email = user.getUser_email();

		Project_MemVo projectMemVo = new Project_MemVo();
		projectMemVo.setPrj_id(prjId);
		projectMemVo.setUser_email(user_email);

		model.addAttribute("data", projectMemService.projectMemList(projectMemVo));
		logger.debug("♬♩♪  프로젝트 설정 누르면 프로젝트 관리로 추가 할 멤버 리스트 불러옴:{}",projectMemService.projectMemList(projectMemVo) );

		return "jsonView";
	}

	// 프로젝트 멤버 관리자로 update
	@RequestMapping("/projectAdmAddAjax")
	public String projectAdmAddAjax(String user_email, String prj_id, Model model, HttpSession session) {
		logger.debug("♬♩♪  projectAdmAddAjax로들어오나요?");
		int prjId = Integer.parseInt(prj_id);

		Project_MemVo projectMemVo = new Project_MemVo();
		projectMemVo.setUser_email(user_email);
		projectMemVo.setPrj_id(prjId);
		projectMemVo.setPrj_mem_lv("LV0");

		int updateCnt = projectMemService.updateProjectMem(projectMemVo);

		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String email = userVo.getUser_email();
		
		Project_MemVo projectMemListVo = new Project_MemVo();
		projectMemListVo.setPrj_id(prjId);
		projectMemListVo.setUser_email(email);
		
		List<Project_MemVo> admList = new ArrayList<Project_MemVo>();
		
		if (updateCnt != 0) {
			List<Project_MemVo> project_adm_list = projectMemService.projectMemList(projectMemListVo);
			for (int i = 0; i < project_adm_list.size(); i++) {
				if (project_adm_list.get(i).getPrj_mem_lv().equals("LV0")) {
					admList.add(project_adm_list.get(i));
				}
			}
			model.addAttribute("data", admList);
		}
		return "jsonView";
	}

	// 프로젝트 관리자 삭제
	@RequestMapping("/projectAdmDelAjax")
	public String projectAdmDelAjax(String user_email, String prj_id, Model model) {

		int prjId = Integer.parseInt(prj_id);

		Project_MemVo projectMemVo = new Project_MemVo();
		projectMemVo.setUser_email(user_email);
		projectMemVo.setPrj_id(prjId);
		projectMemVo.setPrj_mem_lv("LV1");

		int updateCnt = projectMemService.updateProjectMem(projectMemVo);
		List<Project_MemVo> admList = new ArrayList<Project_MemVo>();

		if (updateCnt != 0) {
			List<Project_MemVo> project_adm_list = projectMemService.projectMemList(projectMemVo);
			for (int i = 0; i < project_adm_list.size(); i++) {
				if (project_adm_list.get(i).getPrj_mem_lv().equals("LV0")) {
					admList.add(project_adm_list.get(i));
				}
			}
			model.addAttribute("data", admList);
		}
		return "jsonView";
	}

	// 프로젝트 멤버 리스트 불러오기
	@RequestMapping("/projectMemListAjax")
	public String projectMemListAjax(String prj_id, Model model, HttpSession session) {
		logger.debug("♬♩♪  projectMemListAjax로 들어옵니까?????????");
		int prjId = Integer.parseInt(prj_id);

		// 세션에 저장된 user 정보를 가져옴
		UserVo user = (UserVo) session.getAttribute("USER_INFO");
		String user_email = user.getUser_email();

		Project_MemVo projectMemVo = new Project_MemVo();
		projectMemVo.setPrj_id(prjId);
		projectMemVo.setUser_email(user_email);

		// 내가 속한 프로젝트의 멤버들을 중복 없이 가져와 리스트에 담기
		List<Project_MemVo> project_mem_list = projectMemService.projectAllMemList(user_email);
		List<Project_MemVo> project_adm_list = projectMemService.projectMemList(projectMemVo);

		for (int i = 0; i < project_adm_list.size(); i++) {
			for (int j = 0; j < project_mem_list.size(); j++) {
				if (project_mem_list.get(j).getUser_email()
						.equals(project_adm_list.get(i).getUser_email())) {
					project_mem_list.remove(project_mem_list.get(j));
				}
			}
		}
		model.addAttribute("data", project_mem_list);
		return "jsonView";
	}

	// 프로젝트 멤버 추가
	@RequestMapping("/projectMemAddAjax")
	public @ResponseBody HashMap<String, Object> projectMemAddAjax(String user_email, String prj_id, Model model, HttpSession session) {
		
		int prjId = Integer.parseInt(prj_id);
		
		UserVo user = (UserVo) session.getAttribute("USER_INFO");
		String login_user_email = user.getUser_email();

		Project_MemVo projectMemVo = new Project_MemVo();
		projectMemVo.setPrj_id(prjId);
		projectMemVo.setUser_email(login_user_email);
		
		//생성할 때 체크한 멤버리스트
		List<Project_MemVo> project_MemList = projectMemService.projectMemYNList(projectMemVo);
		logger.debug("♬♩♪  projectMemAddAjax + project_MemList: {}", project_MemList);
		ArrayList<String> user_emailList = new ArrayList<String>();
		for (int i = 0; i < project_MemList.size(); i++) {
			user_emailList.add(project_MemList.get(i).getUser_email());
		}
		logger.debug("♬♩♪  user_emailList: {}", user_emailList);
		logger.debug("♬♩♪  선택한 회원 email: {}", user_email);
		
		int insertCnt = 0;
		for (int i=0; i < user_emailList.size(); i++) {
			if(user_emailList.get(i).contains(user_email)) {
				logger.debug("♬♩♪  update");
				projectMemVo.setPrj_mem_lv("LV1");
				projectMemVo.setPrj_id(prjId);
				projectMemVo.setUser_email(user_email);
			}
			projectMemService.updateProjectMem(projectMemVo);
			
			projectMemVo.setPrj_id(prjId);
			projectMemVo.setUser_email(user_email);
			projectMemVo.setPrj_mem_lv("LV1");
			projectMemVo.setPrj_own_fl("N");
			insertCnt = projectMemService.insertProjectMem(projectMemVo);
		}
		
		//채팅방에 프로젝트 멤버 추가
		ProjectVo vo = new ProjectVo();
		vo.setUser_email(user_email);
		vo.setPrj_id(prjId);
		memService.insertChatMemProject(vo);
		
		Project_MemVo projectMemListVo = new Project_MemVo();
		projectMemListVo.setUser_email(user_email);
		projectMemListVo.setPrj_id(prjId);

		// 내가 속한 프로젝트의 멤버들을 중복 없이 가져와 리스트에 담기
		List<Project_MemVo> project_mem_list = projectMemService.projectAllMemList(user_email);
		List<Project_MemVo> project_adm_list = projectMemService.projectMemList(projectMemListVo);
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		
		if (insertCnt != 0) {
			for (int i = 0; i < project_adm_list.size(); i++) {
				for (int j = 0; j < project_mem_list.size(); j++) {
					if (project_mem_list.get(j).getUser_email()
							.equals(project_adm_list.get(i).getUser_email())) {
						project_mem_list.remove(project_mem_list.get(j));
					}
				}
			}
		}
		hashmap.put("projectAdmList", project_adm_list);
		hashmap.put("projectMemList", project_mem_list);

		return hashmap;
	}

	// 프로젝트 멤버 삭제
	@RequestMapping("/projectMemDelAjax")
	public @ResponseBody HashMap<String, Object> projectMemDelAjax(String user_email, String prj_id, Model model,
			HttpSession session) {

		int prjId = Integer.parseInt(prj_id);

		Project_MemVo projectMemVo = new Project_MemVo();
		projectMemVo.setUser_email(user_email);
		projectMemVo.setPrj_id(prjId);
		projectMemVo.setPrj_mem_lv("LV1");
		
		//프로젝트 채팅방 대화 내역 삭제
		ProjectVo chatOut = new ProjectVo();
		chatOut.setUser_email(user_email);
		chatOut.setPrj_id(prjId);
		
		int content = contentService.outChatContentProject(chatOut);
		//프로젝트 채팅방 멤버 나가기
		int mem = memService.outChatMemProject(chatOut);

		int deleteCnt = projectMemService.deleteProjectMem(projectMemVo);

		// 세션에 저장된 user 정보를 가져옴
		UserVo user = (UserVo) session.getAttribute("USER_INFO");
		String user_email1 = user.getUser_email();

		Project_MemVo projectMemListVo = new Project_MemVo();
		projectMemListVo.setUser_email(user_email1);
		projectMemListVo.setPrj_id(prjId);

		// 내가 속한 프로젝트의 멤버들을 중복 없이 가져와 리스트에 담기
		List<Project_MemVo> project_all_mem_list = projectMemService.projectAllMemList(user_email);
		List<Project_MemVo> project_mem_list = projectMemService.projectMemList(projectMemListVo);

		HashMap<String, Object> hashmap = new HashMap<String, Object>();

		if (deleteCnt != 0) {
			for (int i = 0; i < project_mem_list.size(); i++) {
				for (int j = 0; j < project_all_mem_list.size(); j++) {
					if (project_mem_list.get(i).getUser_email().equals(project_all_mem_list.get(j).getUser_email())) {
						project_all_mem_list.remove(project_all_mem_list.get(j));
					}
				}
			}
		}

		hashmap.put("projectAllMemList", project_all_mem_list);
		hashmap.put("projectMemList", project_mem_list);

		return hashmap;
	}

	// 프로젝트를 생성하는 동시에 프로젝트 멤버에 생성자 insert
	@RequestMapping(path = "/form", method = RequestMethod.POST)
	public String porjectForm(Model model, ProjectVo projectVo, String memList, String templateType, HttpSession session) {

		logger.debug("templateType :::::::::::: log : {}", templateType); 
		
		String viewName = "";
		ArrayList<String> memberList = new ArrayList<String>();
		if(!memList.isEmpty()) {
			memberList = new ArrayList<String>(Arrays.asList(memList.split(",")));
		}

		// 세션에 저장된 user 정보를 가져옴
		UserVo user = (UserVo) session.getAttribute("USER_INFO");
		String user_email = user.getUser_email();
		projectVo.setPrj_st("상태없음");
		
		//프로젝트 생성 
		int insertProjectCnt = projectService.insertProject(projectVo);
		
		//프로젝트 채팅방 생성
		int maxPrj_id = projectService.maxProjectId();
		ProjectVo pVo = projectService.getProject(maxPrj_id);
		Chat_RoomVo roomVo = new Chat_RoomVo(pVo.getPrj_nm(), maxPrj_id);
		int room = roomService.createRoomProject(roomVo);
		
		//프로젝트 관리자 insert
		Project_MemVo projectMemVo = new Project_MemVo();
		projectMemVo.setPrj_id(projectVo.getPrj_id()); 		// 프로젝트관리자 - 프로젝트 ID 셋팅
		projectMemVo.setUser_email(user_email); 			// 프로젝트관리자 - 멤버 이메일 셋팅
		projectMemVo.setPrj_mem_lv("LV0"); 				// 프로젝트관리자 - 멤버 레벨 셋팅
		projectMemVo.setPrj_own_fl("Y"); 				// 프로젝트관리자 - 멤버 소유 유무 셋팅

		int insertProjectAdmCnt = projectMemService.insertProjectMem(projectMemVo);
		
		//프로젝트 채팅방멤버에 관리자 입력
		int chatRoomMax = roomService.maxRoomId();
		Chat_MemVo memVo = new Chat_MemVo(chatRoomMax, user_email);
		int mem = memService.insertChatMem(memVo);
		
		logger.debug("log room : {}, mem : {}",room,mem);
		
		//선택한 멤버가 있을 때
		if(memberList.size() != 0) {
			//선택한 멤버 리스트를 가져와서 반복해서 insert하기
			for (int i = 0; i < memberList.size(); i++) {
				String memEmail = memberList.get(i);
				
				Project_MemVo projectMemListVo = new Project_MemVo();
				projectMemListVo.setPrj_id(projectVo.getPrj_id()); 		// 프로젝트멤버 - 프로젝트 ID 셋팅
				projectMemListVo.setUser_email(memEmail); 			// 프로젝트멤버 - 멤버 이메일 셋팅
				projectMemListVo.setPrj_mem_lv("LV1"); 				// 프로젝트멤버 - 멤버 레벨 셋팅
				projectMemListVo.setPrj_own_fl("N"); 					// 프로젝트멤버 - 멤버 소유 유무 셋팅
				
				projectMemService.insertProjectMem(projectMemListVo);
				
				//프로젝트 채팅방에 멤버 입력
				memVo.setUser_email(memEmail);
				memService.insertChatMem(memVo);
			}
		}
		
		//요일 구하기
		DateFormatSymbols symbols = new DateFormatSymbols();
		String[] dayNames = symbols.getShortWeekdays();
		
		//인서트한 멤버 리스트 가져오기
		projectMemVo.setPrj_id(projectVo.getPrj_id());
		projectMemVo.setUser_email(user_email);
		List<Project_MemVo> projectMemList = projectMemService.projectMemList(projectMemVo);
		
		//템플릿 선택 했을 때
		if(templateType.equals("개인별")) {
			for(Project_MemVo list : projectMemList) {
				Work_ListVo workListVo = new Work_ListVo();
				workListVo.setPrj_id(projectVo.getPrj_id());
				workListVo.setWrk_lst_nm(list.getUser_nm());
				workListService.insertWorkList(workListVo);
			}
		}else if(templateType.equals("요일별")) {
			for(int i=2; i<7; i++) {
				Work_ListVo workListVo = new Work_ListVo();
				workListVo.setPrj_id(projectVo.getPrj_id());
				workListVo.setWrk_lst_nm(dayNames[i]+"요일");
				workListService.insertWorkList(workListVo);
			}
		}

		// 값이 0이 아닐때
		if (insertProjectCnt != 0 && insertProjectAdmCnt != 0) {
			viewName = "/projectList/projectList.user.tiles";
			model.addAttribute("projectList", projectService.projectList(user_email));
		}

		return viewName;
	}

	// 프로젝트 생성시 프로젝트 멤버 리스트 불러오기
	@RequestMapping("/createMemAddAjax")
	public String createMemAddAjax(Model model, HttpSession session) {

		// 세션에 저장된 user 정보를 가져옴
		UserVo user = (UserVo) session.getAttribute("USER_INFO");
		String user_email = user.getUser_email();

		Project_MemVo projectMemVo = new Project_MemVo();
		projectMemVo.setUser_email(user_email);

		// 내가 속한 프로젝트의 멤버들을 중복 없이 가져와 리스트에 담기
		List<Project_MemVo> project_mem_list = projectMemService.projectAllMemList(user_email);
		
		//현재 접속한 사용자 이메일은 제외하고 리스트 가져오기
		for (int j = 0; j < project_mem_list.size(); j++) {
			if (project_mem_list.get(j).getUser_email().equals(user_email)) {
				project_mem_list.remove(project_mem_list.get(j));
			}
		}

		model.addAttribute("data", project_mem_list);

		return "jsonView";
	}

	// 프로젝트 나가기
	@RequestMapping(path = "/leave", method = RequestMethod.POST)
	public String projectLeave(String prj_id, Model model, HttpSession session) {

		String viewName = "";
		int prjId = Integer.parseInt(prj_id);

		// 세션에 저장된 user 정보를 가져옴
		UserVo user = (UserVo) session.getAttribute("USER_INFO");
		String user_email = user.getUser_email();

		Project_MemVo projectMemVo = new Project_MemVo();
		projectMemVo.setUser_email(user_email);
		projectMemVo.setPrj_id(prjId);
		projectMemVo.setPrj_mem_lv("LV1");
		
		//프로젝트 채팅방 대화 내역 삭제
		ProjectVo chatOut = new ProjectVo();
		chatOut.setUser_email(user_email);
		chatOut.setPrj_id(prjId);
		
		int content = contentService.outChatContentProject(chatOut);
		//프로젝트 채팅방 멤버 나가기
		int mem = memService.outChatMemProject(chatOut);
		
		logger.debug("log content: {}, mem : {}",content,mem);
		int deleteCnt = projectMemService.deleteProjectMem(projectMemVo);
		if (deleteCnt != 0) {
			viewName = "redirect:/project/list";
		}

		return viewName;
	}

	// 프로젝트 삭제
	@RequestMapping(path = "delete", method = RequestMethod.POST)
	public String projectDelete(String prj_id, Model model, HttpSession session) {

		logger.debug("prj_id ::::::::::::: log {}", prj_id);
		String viewName = "";
		int prjId = Integer.parseInt(prj_id);

		ProjectVo projectVo = new ProjectVo();
		projectVo.setPrj_id(prjId);

		//채팅방 내용 삭제
		contentService.deleteChatContentProject(prjId);
		
		//채팅방 멤버 삭제
		memService.deleteChatMemProject(prjId);
		
		//채팅방 삭제
		roomService.deleteChatRoomProject(prjId);
		
		int deleteCnt = projectService.deleteProject(projectVo);
		
		if (deleteCnt != 0) {
			viewName = "redirect:/project/list";
		}

		return viewName;
	}
	
	// 프로젝트 리스트 조회
	@RequestMapping(path = "/headerSearch", method = RequestMethod.GET)
	public String searchWorkList(Model model, HttpSession session, String headerSearch, String headerSearchText) {

		logger.debug("log headerSearch headerSearchText: {} {}",headerSearch,headerSearchText);
		// 세션에 저장된 user 정보를 가져옴
		UserVo user = (UserVo) session.getAttribute("USER_INFO");
		String user_email = user.getUser_email();
		
		List<ProjectVo> projectList = new ArrayList<ProjectVo>();
		// 업무리스트명으로 프로젝트 검색 
		if(headerSearch.equals("1")) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("user_email", user_email);
			map.put("wrk_lst_nm",headerSearchText);
			
			projectList = projectService.searchWorkList(map);
			
		}
		//업무명으로 프로젝트 검색
		else if(headerSearch.equals("2")) {
			logger.debug("log headerSearch headerSearchText: {} {}",headerSearch,headerSearchText);
			WorkVo workVo = new WorkVo();
			workVo.setUser_email(user_email);
			workVo.setWrk_nm(headerSearchText);
			
			projectList = projectService.searchWorkNm(workVo);
			
		}
		//프로젝트 멤버명으로 프로젝트 검색
		else if(headerSearch.equals("3")) {
			UserVo userVo = new UserVo();
			userVo.setUser_email(user_email);
			userVo.setUser_nm(headerSearchText);
			
			projectList = projectService.searchProjectMem(userVo);
			
		}
		
		model.addAttribute("projectList", projectList);

		return "/projectList/projectList.user.tiles";
	}
	
	
	/**
	 * Method 		: searchName
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-28 최초 생성
	 * @param session
	 * @param model
	 * @param user_nm
	 * @return
	 * Method 설명 	: 프로젝트 생성시 멤버 검색
	 */
	@RequestMapping("/searchName")
	String searchName(HttpSession session, Model model, String user_nm) {
		logger.debug("♬♩♪  searchName user_nm: {}", user_nm);
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		
		Project_MemVo project_memVo = new Project_MemVo();
		project_memVo.setUser_email(user_email);
		project_memVo.setUser_nm(user_nm);
		
		List<Project_MemVo> project_memList = projectService.searchName(project_memVo);
		logger.debug("♬♩♪  누굴까요?: {}", project_memList);
		
		model.addAttribute("data", project_memList);
		
		return "jsonView";
	}
	
	/**
	 * Method 		: searchPL
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-28 최초 생성
	 * @param session
	 * @param model
	 * @param user_nm
	 * @return
	 * Method 설명 	: 프로젝트 생성후 설정에서 관리자 검색
	 */
	@RequestMapping("/searchPL")
	String searchPL(HttpSession session, Model model, String user_nm) {
		logger.debug("♬♩♪  searchPL user_nm: {}", user_nm);
		ProjectVo projectVo = (ProjectVo) session.getAttribute("PROJECT_INFO");
		int prj_id = projectVo.getPrj_id();
		logger.debug("♬♩♪  prj_id: {}", prj_id);
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		
		Project_MemVo project_memVo = new Project_MemVo();
		project_memVo.setUser_email(user_email);
		project_memVo.setPrj_id(prj_id);
		project_memVo.setUser_nm(user_nm);
		
		List<Project_MemVo> project_memList = projectService.searchPL(project_memVo);
		
		logger.debug("♬♩♪  누굴까요?: {}", project_memList);
		
		model.addAttribute("data", project_memList);
		
		return "jsonView";
	}
	
	/**
	 * Method 		: searchMem
	 * 작성자 			: 손영하
	 * 변경이력 		: 2019-08-29 최초 생성
	 * @param session
	 * @param model
	 * @param user_nm
	 * @return
	 * Method 설명 	: 프로젝트 멤버 추가 삭제
	 */
	@RequestMapping("/searchMem")
	String searchMem(HttpSession session, Model model, String user_nm) {
		logger.debug("♬♩♪  searchName user_nm: {}", user_nm);
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String user_email = userVo.getUser_email();
		
		Project_MemVo project_memVo = new Project_MemVo();
		project_memVo.setUser_email(user_email);
		project_memVo.setUser_nm(user_nm);
		
		List<Project_MemVo> project_memList = projectService.searchName(project_memVo);
		logger.debug("♬♩♪  누굴까요?: {}", project_memList);
		
		model.addAttribute("data", project_memList);
		
		return "jsonView";
	}
	
	
	
	

}
