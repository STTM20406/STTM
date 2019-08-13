package kr.or.ddit.work_list.contoller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	public String projectView(String prj_id, Model model, HttpSession session) {
		
		int prjId = Integer.parseInt(prj_id);
		ProjectVo projectVo = projectService.getProject(prjId);
		
		//프로젝트에 해당하는 업무 리스트 조회
		List<Work_ListVo> workList = workListService.workList(prjId);
		
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
		
		return "/main/work/work.user.tiles";
	}
	
	// 업무리스트 추가
	@RequestMapping("/workListAddAjax")
	public @ResponseBody HashMap<String, Object> workListAddAjaxString(String wrk_lst_nm, Model model, HttpSession session) {
		
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
	public @ResponseBody HashMap<String, Object> workListDelAjax(String wrk_lst_id, Model model, HttpSession session) {
		
		int wrkListID = Integer.parseInt(wrk_lst_id);
		
		//세션에 저장된 프로젝트 정보를 가져옴
		ProjectVo projectVo = (ProjectVo) session.getAttribute("PROJECT_INFO");
		int prj_id = projectVo.getPrj_id();
		
		//프로젝트의 해당하는 업무리스트 아이디가 업무 테이블에 포함되어있으면 업데이트 안됨
		List<WorkVo> workList = workService.getWork(wrkListID);
		
		int deleteCnt = 0;
		
		//하위 업무가 없을 때 삭제
		if(workList.size() == 0) {
			deleteCnt = workListService.deleteWorkList(wrkListID);
		}
				
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
		
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		if(deleteCnt != 0) {
			hashmap.put("workList", workListItem);
			hashmap.put("works", works);
		}

		return hashmap;
	}

}
