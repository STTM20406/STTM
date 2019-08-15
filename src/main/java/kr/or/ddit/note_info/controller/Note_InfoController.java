package kr.or.ddit.note_info.controller;

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

import kr.or.ddit.note_content.model.Note_ContentVo;
import kr.or.ddit.note_info.model.Note_InfoVo;
import kr.or.ddit.note_info.service.INote_InfoService;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.users.model.UserVo;

@Controller
public class Note_InfoController {

	private static final Logger logger = LoggerFactory.getLogger(Note_InfoController.class);
	
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
	
	@RequestMapping(path="/noteWrite",method = RequestMethod.GET)
	public String noteWrite(HttpSession session,Model model) {
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		model.addAttribute("send_email", userVo.getUser_email());
		
		return "/note/noteWrite.user.tiles";
	}
	
	@RequestMapping(path="/noteWrite",method = RequestMethod.POST)
	public String noteWrite(Note_ContentVo conVo,Model model,String sendEmail,String rcvEmail,String smarteditor) {
		String viewName = "";
		
		conVo.setNote_con_id(conVo.getNote_con_id());
		conVo.setNote_con(smarteditor);
		int contentCnt = noteService.insertNoteContent(conVo);
		Note_InfoVo infoVo = new Note_InfoVo();
		infoVo.setNote_con_id(conVo.getNote_con_id());
		infoVo.setRcv_email(rcvEmail);
		infoVo.setSend_email(sendEmail);
		
		if(contentCnt ==1) {
			int infoCnt = noteService.insertNoteInfo(infoVo);
			
			if(infoCnt == 1) {
				viewName ="redirect:/noteList";
			}
		}
		
		return viewName;
	}
	
	@RequestMapping(path="/rcvNoteWrite",method = RequestMethod.GET)
	public String rcvNoteWrite(HttpSession session,Model model,String rcvEmail) {
		logger.debug("!@# rcvEmail : {}",rcvEmail);
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		model.addAttribute("send_email", userVo.getUser_email());
		model.addAttribute("rcv_email", rcvEmail);
		
		return "/note/rcvNoteWrite.user.tiles";
	}
	
	@RequestMapping(path="/rcvNoteWrite",method = RequestMethod.POST)
	public String rcvNoteWrite(Note_ContentVo conVo,Model model,String sendEmail,String rcvEmail,String smarteditor) {
		String viewName = "";
		
		conVo.setNote_con_id(conVo.getNote_con_id());
		conVo.setNote_con(smarteditor);
		int contentCnt = noteService.insertNoteContent(conVo);
		Note_InfoVo infoVo = new Note_InfoVo();
		infoVo.setNote_con_id(conVo.getNote_con_id());
		infoVo.setRcv_email(rcvEmail);
		infoVo.setSend_email(sendEmail);
		
		if(contentCnt ==1) {
			int infoCnt = noteService.insertNoteInfo(infoVo);
			
			if(infoCnt == 1) {
				viewName ="redirect:/noteList";
			}
		}
		
		return viewName;
	}
	
}
