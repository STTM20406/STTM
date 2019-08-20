package kr.or.ddit.work_list.contoller;

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

import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.project.model.ProjectVo;
import kr.or.ddit.project.service.IProjectService;
import kr.or.ddit.project_mem.model.Project_MemVo;
import kr.or.ddit.project_mem.service.IProject_MemService;
import kr.or.ddit.users.model.UserVo;
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
	
	@Resource(name = "project_MemService")
	private IProject_MemService projectMemService;
	
	
	//GET방식으로 업무리스트 및 업무 조회
	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public String projectViewGet(Model model,  UserVo userVo, ProjectVo prjVO, HttpSession session) {
		
		userVo = (UserVo) session.getAttribute("USER_INFO");
		prjVO = (ProjectVo) session.getAttribute("PROJECT_INFO");
		
		int prj_id =prjVO.getPrj_id();
		
		ProjectVo projectVo = projectService.getProject(prjVO.getPrj_id());
		
		// 현재 접속한 사용자의 프로젝트 멤버 정보 매칭해서 가져오기
		Project_MemVo projectMemInfo = new Project_MemVo(prj_id, userVo.getUser_email());
		Project_MemVo userInfo = projectMemService.getProjectMemInfo(projectMemInfo);
		
		//프로젝트에 해당하는 업무 리스트 조회
		List<Work_ListVo> workList = workListService.workList(prj_id);
		
		List<WorkVo> work = new ArrayList<WorkVo>();
		List<WorkVo> works = new ArrayList<WorkVo>();
		
		/*
			해당 업무리스트에서 업무리스트 ID 가져서와 업무테이블의
			테이블의 업무리스트 ID 매칭하여 해당 업무 가져오기
		*/
		for(int i=0; i<workList.size(); i++) {
			int wrkListId = workList.get(i).getWrk_lst_id();
			work = workService.getWork(wrkListId);
			for(int j=0; j<work.size(); j++) {
				//업무리스트 ID가 같으면 해당 업무를 가져와서 담기
				works.add(work.get(j)); 
			}
		}
		
		
		//선택한 프로젝트의 정보를 세션에 담음 
		session.setAttribute("PROJECT_INFO", projectVo);
		model.addAttribute("workList", workList);
		model.addAttribute("works", works);
		model.addAttribute("userInfo", userInfo);
		
		return "/main/work/work.user.tiles";
	}
	
	// 프로젝트 리스트 조회
	@RequestMapping(path = "/list", method = RequestMethod.POST)
	public String projectView(String prj_id, WorkVo workVo, UserVo userVo, ProjectVo projectVo, Model model, HttpSession session) {
		
		userVo = (UserVo) session.getAttribute("USER_INFO");
		
		int prjId = Integer.parseInt(prj_id);
		projectVo = projectService.getProject(prjId);
		
		//프로젝트에 해당하는 업무 리스트 조회
		List<Work_ListVo> workList = workListService.workList(prjId);
		
		List<WorkVo> work = new ArrayList<WorkVo>();
		List<WorkVo> works = new ArrayList<WorkVo>();
		
		/*
			여러개의 업무리스트 ID 가져서와 업무 테이블의
			업무리스트 ID 매칭하여 해당 업무 가져오기
		*/
		for(int i=0; i<workList.size(); i++) {
			int wrkListId = workList.get(i).getWrk_lst_id();
			work = workService.getWork(wrkListId);
			for(int j=0; j<work.size(); j++) {
				//업무리스트 ID가 같으면 해당 업무를 가져와서 담기
				works.add(work.get(j)); 
			}
		}
		
		// 현재 접속한 사용자의 프로젝트 멤버 정보 매칭해서 가져오기
		Project_MemVo projectMemInfo = new Project_MemVo(prjId, userVo.getUser_email());
		Project_MemVo userInfo = projectMemService.getProjectMemInfo(projectMemInfo);
		
		//선택한 프로젝트의 정보를 세션에 담음 
		session.setAttribute("PROJECT_INFO", projectVo);
		model.addAttribute("workList", workList);
		model.addAttribute("works", works);
		model.addAttribute("userInfo", userInfo);
		
		return "/main/work/work.user.tiles";
	}
	
	// 업무리스트 추가
	@RequestMapping("/workListAddAjax")
	public @ResponseBody HashMap<String, Object> workListAddAjaxString(String wrk_lst_nm, HttpSession session) {
		
		//세션에 저장된 프로젝트 정보를 가져옴
		ProjectVo projectVo = (ProjectVo) session.getAttribute("PROJECT_INFO");
		int prj_id = projectVo.getPrj_id();
		
		Work_ListVo workListVo = new Work_ListVo();
		workListVo.setPrj_id(prj_id);
		workListVo.setWrk_lst_nm(wrk_lst_nm);
		
		int insertCnt = workListService.insertWorkList(workListVo);
		
		List<Work_ListVo> workList = workListService.workList(prj_id);
		
		List<WorkVo> work = new ArrayList<WorkVo>();
		List<WorkVo> works = new ArrayList<WorkVo>();
		
		/*
			해당 업무리스트에서 업무리스트 ID 가져서와 업무테이블의
			테이블의 업무리스트 ID 매칭하여 해당 업무 가져오기
		*/
		for(int i=0; i<workList.size(); i++) {
			int wrkListId = workList.get(i).getWrk_lst_id();
			work = workService.getWork(wrkListId);
			for(int j=0; j<work.size(); j++) {
				//업무리스트 ID가 같으면 해당 업무를 가져와서 담기
				works.add(work.get(j)); 
			}
		}
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		if(insertCnt != 0) {
			hashmap.put("workList", workList);
			hashmap.put("works", works);
		}

		return hashmap;
	}
	
	//업무리스트 삭제
	@RequestMapping("/workListDelAjax")
	public @ResponseBody HashMap<String, Object> workListDelAjax(String wrk_lst_id, HttpSession session) {
		
		int wrkListID = Integer.parseInt(wrk_lst_id);
		
		//세션에 저장된 프로젝트 정보를 가져옴
		ProjectVo projectVo = (ProjectVo) session.getAttribute("PROJECT_INFO");
		int prj_id = projectVo.getPrj_id();
		
		//프로젝트의 해당하는 업무리스트 아이디가 업무 테이블에 포함되어있으면 업데이트 안됨
		List<WorkVo> workList = workService.getWork(wrkListID);
		
		int deleteCnt = 0;
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();

		//하위 업무가 없을 때 삭제
		if(workList.size() == 0) {
			deleteCnt = workListService.deleteWorkList(wrkListID);
			
			List<Work_ListVo> workListItem = workListService.workList(prj_id);
			List<WorkVo> work = new ArrayList<WorkVo>();
			List<WorkVo> works = new ArrayList<WorkVo>();
			
			/*
				해당 업무리스트에서 업무리스트 ID 가져서와 업무테이블의
				테이블의 업무리스트 ID 매칭하여 해당 업무 가져오기
			*/
			for(int i=0; i<workListItem.size(); i++) {
				int wrkListId = workListItem.get(i).getWrk_lst_id();
				work = workService.getWork(wrkListId);
				for(int j=0; j<work.size(); j++) {      
					//업무리스트 ID가 같으면 해당 업무를 가져와서 담기
					works.add(work.get(j)); 
				}
			}
			
			if(deleteCnt != 0) {
				hashmap.put("workList", workListItem);
				hashmap.put("works", works);
			}
		}else {
			hashmap.put("noResult", "");
		}

		return hashmap;
	}
	/**
	 * 
	* Method : timerWorkListPagingList
	* 작성자 : 김경호
	* 변경이력 : 2019-08-13
	* @param pageVo
	* @param model
	* @param session
	* @return
	* Method 설명 : 타이머 - 프로젝트, 업무리스트, 업무 페이징 리스트 조회 
	 */
	@RequestMapping(path = "/timerWorkList", method = RequestMethod.GET)
	public String timerWorkListPagingList(PageVo pageVo, Model model, HttpSession session) {
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		logger.debug("userVo : 오랜만에 로거 찍자1 {}",userVo);
		
		String user_email = userVo.getUser_email();
		logger.debug("user_email : 오랜만에 로거 찍자2 {}",user_email);
		
		Map<String, Object> timerMap = new HashMap<String, Object>();
		timerMap.put("page", pageVo.getPage());
		timerMap.put("pageSize", pageVo.getPageSize());
		timerMap.put("user_email", userVo.getUser_email());
		
		logger.debug("timerMap : 타이머맵 {}",timerMap);
		
		Map<String, Object> resultMap = workListService.timerWorkListPagingList(timerMap);
		logger.debug("resultMap : 오랜만에 로거 찍자3 {}",resultMap);
		
		List<Work_ListVo> workList = (List<Work_ListVo>) resultMap.get("workList");
		int paginationSize = (Integer) resultMap.get("paginationSize");
		logger.debug("workList : 오랜만에 로거 찍자4 {}",workList);
		logger.debug("paginationSize : 오랜만에 로거 찍자5 {}",paginationSize);
		
		model.addAttribute("user_email", user_email);
		model.addAttribute("workList", workList);
		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo", pageVo);
		
		return "/timer/timer.user.tiles";
	}
	
	@RequestMapping("/workListNameUpdateAjax")
	public @ResponseBody HashMap<String, Object> workListNameUpdateAjax(Work_ListVo workListVo, String wrk_lst_nm, String wrk_lst_id, HttpSession session) {
		
		int wrkLstId = Integer.parseInt(wrk_lst_id);
		
		workListVo.setWrk_lst_id(wrkLstId);
		workListVo.setWrk_lst_nm(wrk_lst_nm);
		
		//세션에 저장된 프로젝트 정보를 가져옴
		ProjectVo projectVo = (ProjectVo) session.getAttribute("PROJECT_INFO");
		int prj_id = projectVo.getPrj_id();
		
		List<Work_ListVo> workList = workListService.workList(prj_id);
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		
		List<WorkVo> work = new ArrayList<WorkVo>();
		List<WorkVo> works = new ArrayList<WorkVo>();
		
		/*
			해당 업무리스트에서 업무리스트 ID 가져서와 업무테이블의
			테이블의 업무리스트 ID 매칭하여 해당 업무 가져오기
		*/
		for(int i=0; i<workList.size(); i++) {
			int wrkListId = workList.get(i).getWrk_lst_id();
			work = workService.getWork(wrkListId);
			for(int j=0; j<work.size(); j++) {
				//업무리스트 ID가 같으면 해당 업무를 가져와서 담기
				works.add(work.get(j)); 
			}
		}
		
		int updateCnt = workListService.updateWorkList(workListVo);
		if(updateCnt != 0) {
			hashmap.put("workList", workList);
			hashmap.put("works", works);
		}
		
		return hashmap;
	}
	
	
	//업무 생성 ajax
	@RequestMapping("/wrkCreateAjax")
	public @ResponseBody HashMap<String, Object> wrkCreateAjax(WorkVo workVo, String wrk_nm, String wrk_lst_id,  HttpSession session) {
		
		//세션에 저장된 프로젝트 정보를 가져옴
		ProjectVo projectVo = (ProjectVo) session.getAttribute("PROJECT_INFO");
		int prj_id = projectVo.getPrj_id();
		
		//세션에 저장된 현재 접속한 사용자의 정보를 가져옴
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		
		workVo.setWrk_lst_id( Integer.parseInt(wrk_lst_id));
		workVo.setUser_email(userVo.getUser_email());
		workVo.setWrk_nm(wrk_nm);
		int insertCnt = workService.insertWork(workVo);
		
		List<Work_ListVo> workList = workListService.workList(prj_id);
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		List<WorkVo> work = new ArrayList<WorkVo>();
		List<WorkVo> works = new ArrayList<WorkVo>();
		
		/*
			해당 업무리스트에서 업무리스트 ID 가져서와 업무테이블의
			테이블의 업무리스트 ID 매칭하여 해당 업무 가져오기
		*/
		for(int i=0; i<workList.size(); i++) {
			int wrkListId = workList.get(i).getWrk_lst_id();
			work = workService.getWork(wrkListId);
			for(int j=0; j<work.size(); j++) {
				//업무리스트 ID가 같으면 해당 업무를 가져와서 담기
				works.add(work.get(j)); 
			}
		}
		
		if(insertCnt != 0) {
			hashmap.put("workList", workList);
			hashmap.put("works", works);
		}
		
		return hashmap;
	}
	
	
	//업무리스트의 업무를 다른 업무리스트로 이동시 ajax
	@RequestMapping("/wrkTransAjax")
	public String wrkTransAjax(WorkVo workVo, String wrk_id, String wrk_lst_id, HttpSession session) {
		
		String viewNmae = "";
		
		workVo.setWrk_lst_id(Integer.parseInt(wrk_lst_id));
		workVo.setWrk_id(Integer.parseInt(wrk_id));
		int updateCnt = workService.updateWorkID(workVo);
		
		if(updateCnt != 0) {
			viewNmae = "jsonView";
		}
		
		return viewNmae;
	}
	
	//업무 클릭시 설정창에 업무 정보 셋팅
	@RequestMapping("/propertyWorkSetAjax")
	public @ResponseBody HashMap<String, Object> propertyWorkSetAjax(String wrk_id, HttpSession session) {
		
		int wrkID = Integer.parseInt(wrk_id);
		WorkVo workVo = workService.getWorkInfo(wrkID);
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("workVo", workVo);

		return hashmap;
	}
	
	@RequestMapping("/completeCheckAjax")
	public @ResponseBody HashMap<String, Object> completeCheckAjax(String wrk_id, String wrk_cmp_fl, HttpSession session) {
		
		//세션에 저장된 프로젝트 정보를 가져옴
		ProjectVo projectVo = (ProjectVo) session.getAttribute("PROJECT_INFO");
		int prj_id = projectVo.getPrj_id();
		
		
		int wrkID = Integer.parseInt(wrk_id);
		WorkVo workInfo = workService.getWorkInfo(wrkID);
		
		WorkVo workVo = new  WorkVo();
		workVo.setWrk_id(wrkID);
		workVo.setWrk_cmp_fl(wrk_cmp_fl);
		
		if(wrk_cmp_fl.equals("N")) {
			workVo.setWrk_cmp_dt(workInfo.getWrk_dt());
		}else {
			workInfo.setWrk_cmp_dt(null);
		}
		
		int updateCnt = workService.updateWorkCmp(workVo);
		List<Work_ListVo> workList = workListService.workList(prj_id);
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		List<WorkVo> work = new ArrayList<WorkVo>();
		List<WorkVo> works = new ArrayList<WorkVo>();
		
		/*
			해당 업무리스트에서 업무리스트 ID 가져서와 업무테이블의
			테이블의 업무리스트 ID 매칭하여 해당 업무 가져오기
		*/
		for(int i=0; i<workList.size(); i++) {
			int wrkListId = workList.get(i).getWrk_lst_id();
			work = workService.getWork(wrkListId);
			for(int j=0; j<work.size(); j++) {
				//업무리스트 ID가 같으면 해당 업무를 가져와서 담기
				works.add(work.get(j)); 
			}
		}
		
		if(updateCnt != 0) {
			hashmap.put("workList", workList);
			hashmap.put("works", works);
		}
		
		return hashmap;
	}
	
	
	// 프로젝트 설정 업데이트
	@RequestMapping("/propertyWorkSetItemAjax")
	public String propertyWorkSetItemAjax(String wrk_id, String wrk_nm, String wrk_grade, String wrk_color_cd, String wrk_start_dt, String wrk_end_dt, 
									   Model model, HttpSession session) throws ParseException {
		
		int wrkID = Integer.parseInt(wrk_id);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm");

		WorkVo workVo = new WorkVo(wrkID, wrk_nm, wrk_grade, wrk_color_cd);
		

		if (wrk_nm.contentEquals("")) {
			workVo.setWrk_nm(workVo.getWrk_nm());
		}
		
		if (!wrk_grade.contentEquals("")) {
			workVo.setWrk_grade(wrk_grade);
		}
		
		if (!wrk_color_cd.contentEquals("")) {
			workVo.setWrk_color_cd(wrk_color_cd);
		}else {
			workVo.setWrk_color_cd("");
		}
		
		if (!wrk_start_dt.contentEquals("")) {
			workVo.setWrk_start_dt(sdf.parse(wrk_start_dt));
		}
		
		if (!wrk_end_dt.contentEquals("")) {
			workVo.setWrk_end_dt(sdf.parse(wrk_end_dt));
		}
		
		int updateCnt = workService .updateAllWork(workVo);

		if (updateCnt != 0) {
			model.addAttribute("data", workService.getWorkInfo(wrkID));
		}
		
		return "jsonView";
	}
	
}
