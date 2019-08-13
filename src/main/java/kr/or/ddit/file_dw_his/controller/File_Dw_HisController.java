package kr.or.ddit.file_dw_his.controller;

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

import kr.or.ddit.file_dw_his.model.File_Dw_HisVo;
import kr.or.ddit.file_dw_his.service.IFile_Dw_HisService;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.project.model.ProjectVo;

@Controller
public class File_Dw_HisController {

	private static final Logger logger = LoggerFactory.getLogger(File_Dw_HisController.class);

	@Resource(name="file_Dw_HisService")
	private IFile_Dw_HisService file_Dw_HisService;
	
	//개인 보관함용 conrtoller
	@RequestMapping(path="/individualBox", method = RequestMethod.GET)
	String individualBox() {
		logger.debug("♬♩♪  개인보관함 긔긔 ");
		return "/main/fileLink/FileLinkIndividualSave.user.tiles";
	}
	
	//다운로드 기록! controller
	@RequestMapping(path="/historyPagination",method = RequestMethod.GET)
	String historyPagination(Model model, PageVo pageVo, HttpSession session) {
		ProjectVo projectVO = (ProjectVo) session.getAttribute("PROJECT_INFO");
		int prj_id = projectVO.getPrj_id();
//		int prj_id = 1;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		map.put("prj_id", prj_id);
		
		Map<String, Object> resultMap = file_Dw_HisService.historyPagination(map);
		List<File_Dw_HisVo> historyList = (List<File_Dw_HisVo>) resultMap.get("historylist");
		int paginationSize = (Integer) resultMap.get("paginationSize");
		
		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo", pageVo);

		model.addAttribute("historyList", historyList);
		model.addAttribute("prj_id", prj_id);
		
		return "/main/fileLink/fileUseHistory.user.tiles";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

