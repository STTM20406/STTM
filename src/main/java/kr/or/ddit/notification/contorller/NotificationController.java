package kr.or.ddit.notification.contorller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.classic.Logger;
import kr.or.ddit.notification.dao.INotificationDao;
import kr.or.ddit.notification.model.NotificationReciverVo;
import kr.or.ddit.notification.service.INotificationService;
import kr.or.ddit.paging.model.PageVo;
import kr.or.ddit.users.model.UserVo;
import kr.or.ddit.users.service.IUserService;

@Controller
public class NotificationController {
	private static final Logger logger = (Logger) LoggerFactory.getLogger(NotificationController.class);
	
	@Resource(name="notificationService")
	private INotificationService notifiService;
	
	@Resource(name="userService")
	private IUserService userService;
	
	@RequestMapping("/notification")
	public String notifiList(Model model,HttpSession session,String page,String pageSize) {
		
		UserVo userVo = (UserVo) session.getAttribute("USER_INFO");
		
		int resetCount = userService.resetCount(userVo.getUser_email());
		userVo.setCount_notify(0);
		session.setAttribute("USER_INFO", userVo);
		
		logger.debug("!@# USER_INFO : {}@@@@@@@@@@@@@@@@@@@@@@@@@", userVo);
		
		int pageNm = page == null ? 1 : Integer.parseInt(page);
//		int pageSizeNm = pageSize == null ? 10 : Integer.parseInt(pageSize);
		int pageSizeNm = 10;
		
		PageVo pageVo = new PageVo(pageNm, pageSizeNm);
		pageVo.setRcv_email(userVo.getUser_email());
		
		Map<String, Object> resultMap = notifiService.notifiList(pageVo);
		List<NotificationReciverVo> notifiList = (List<NotificationReciverVo>) resultMap.get("notifiList");
		
		int notifiPageSize = (int) resultMap.get("notifiPageSize");
		
		model.addAttribute("notifiList", notifiList);
		model.addAttribute("notifiPageSize", notifiPageSize);
		
		return "/notification/list.user.tiles";
	}
	
}
