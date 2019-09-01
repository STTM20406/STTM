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
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.ddit.file_dw_his.model.File_Dw_HisVo;
import kr.or.ddit.file_dw_his.service.IFile_Dw_HisService;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.project.model.ProjectVo;

@Controller
public class File_Dw_HisController {

	private static final Logger logger = LoggerFactory.getLogger(File_Dw_HisController.class);

	@Resource(name="file_Dw_HisService")
	private IFile_Dw_HisService file_Dw_HisService;
	
	//다운로드 기록! controller
	@RequestMapping("/historyPagination")
	String historyPagination(Model model, HttpSession session, 
		@RequestParam(name = "page", defaultValue = "1")int page,@RequestParam(name = "pageSize", defaultValue = "10")int pageSize) {
		PageVo pageVo = new PageVo(page, pageSize);
		
		ProjectVo projectVO = (ProjectVo) session.getAttribute("PROJECT_INFO");
		int prj_id = projectVO.getPrj_id();
		logger.debug("♬♩♪  historyPagination + prj_id : {}", prj_id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", pageVo.getPage());
		map.put("pageSize", pageVo.getPageSize());
		map.put("prj_id", prj_id);
		logger.debug("♬♩♪  map: {}", map);
		
		
		Map<String, Object> resultMap = file_Dw_HisService.historyPagination(map);
		List<File_Dw_HisVo> historyList = (List<File_Dw_HisVo>) resultMap.get("historyList");
		
		logger.debug("♬♩♪  historyPagination + historyList: {}", historyList);
		int paginationSize = (Integer) resultMap.get("paginationSize");
		
		model.addAttribute("paginationSize", paginationSize);
		model.addAttribute("pageVo", pageVo);

		model.addAttribute("historyList", historyList);
		model.addAttribute("prj_id", prj_id);
		
		return "jsonView";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

