package kr.or.ddit.note_info.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.note_info.model.Note_InfoVo;
import kr.or.ddit.note_info.service.INote_InfoService;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.users.model.UserVo;

@Controller
public class Note_InfoController {

	@Resource(name="note_InfoService")
	private INote_InfoService noteService;
	
	@RequestMapping("/noteList")
	public String noteList(Model model,HttpSession session,String page,String pageSize) {
		
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		String userId = userVo.getUser_email();
		
		int pageStr = page == null ? 1 : Integer.parseInt(page);
		int pageSizeStr =  pageSize == null ? 10 : Integer.parseInt(pageSize);
		
		PageVo pageVo = new PageVo(pageStr,pageSizeStr);
		pageVo.setRcv_email(userId);
		pageVo.setSend_email(userId);

		Note_InfoVo noteVo = new Note_InfoVo();
		
		Map<String, Object> rcvMap = noteService.rcvList(pageVo); 
		List<Note_InfoVo> rcvList = (List<Note_InfoVo>) rcvMap.get("rcvList");
		int rcvPaginationSize = (int) rcvMap.get("rcvPaginationSize");
		
		Map<String, Object> sendMap = noteService.sendList(pageVo);
		List<Note_InfoVo> sendList = (List<Note_InfoVo>) sendMap.get("sendList");
		int sendPaginationSize = (int) sendMap.get("sendPaginationSize");
		model.addAttribute("rcvList", rcvList);
		model.addAttribute("rcvPaginationSize", rcvPaginationSize);
		
		model.addAttribute("sendList", sendList);
		model.addAttribute("sendPaginationSize", sendPaginationSize);
		model.addAttribute("pageVo", pageVo);
//		
		
		return "/note/noteList.user.tiles";
	}
	
	@RequestMapping("/noteWrite")
	public String noteWrite(Model model) {
		
		return "/note/noteWrite.user.tiles";
	}
	
	
}
