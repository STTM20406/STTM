package kr.or.ddit.chat_content.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.ddit.chat_content.model.Chat_ContentVo;
import kr.or.ddit.chat_content.service.Chat_ContentService;
import kr.or.ddit.users.model.UserVo;

@Controller
public class Chat_ContentController {

	@Resource(name="chat_ContentService")
	private Chat_ContentService contentService;
	

	
}
